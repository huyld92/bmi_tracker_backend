/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.exceptions.DuplicateRecordException;
import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.Commission;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.Transaction;
import com.fu.bmi_tracker.model.entities.AdvisorSubscription;
import com.fu.bmi_tracker.model.entities.CommissionAllocation;
import com.fu.bmi_tracker.model.entities.Package;
import com.fu.bmi_tracker.model.enums.ESubscriptionStatus;
import com.fu.bmi_tracker.model.enums.EPaymentStatus;
import com.fu.bmi_tracker.payload.request.CreateSubscriptionTransactionRequest;
import com.fu.bmi_tracker.payload.response.AdvisorSubscriptionSummary;
import com.fu.bmi_tracker.payload.response.AdvisorDetailsResponse;
import com.fu.bmi_tracker.payload.response.CountSubscriptionResponse;
import com.fu.bmi_tracker.payload.response.SubscriptionSummaryResponse;
import com.fu.bmi_tracker.repository.AdvisorRepository;
import com.fu.bmi_tracker.repository.CommissionAllocationRepository;
import com.fu.bmi_tracker.repository.CommissionRepository;
import com.fu.bmi_tracker.repository.MemberRepository;
import com.fu.bmi_tracker.util.DateTimeUtils;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.repository.TransactionRepository;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import com.fu.bmi_tracker.services.SubscriptionService;
import com.fu.bmi_tracker.repository.SubscriptionRepository;
import com.fu.bmi_tracker.util.CommissionRateUtils;
import com.fu.bmi_tracker.repository.PackageRepository;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AdvisorRepository advisorRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CommissionRepository commissionRepository;

    @Autowired
    CommissionAllocationRepository commissionAllocationRepository;

    @Autowired
    PackageRepository packageRepository;

    @Autowired
    DateTimeUtils dateTimeUtils;

    @Override
    public Iterable<AdvisorSubscription> findAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Optional<AdvisorSubscription> findById(Integer id) {
        return subscriptionRepository.findById(id);
    }

    @Override
    public AdvisorSubscription save(AdvisorSubscription t) {
        return subscriptionRepository.save(t);
    }

    @Override
    public Iterable<AdvisorSubscription> getSubscriptionByMemberAccountID(Integer accountID) {
        return subscriptionRepository.findByMember_Account_AccountID(accountID);
    }

    @Override
    public Iterable<AdvisorSubscription> getSubscriptionByMemberID(Integer memberID) {
        return subscriptionRepository.findByMemberMemberID(memberID);

    }

    @Override
    public Iterable<AdvisorSubscription> getSubscriptionByAdvisorIDAndMonth(Integer advisorID, String month) {
        // Chuyển đổi chuỗi "yyyy-MM" thành YearMonth
        YearMonth yearMonthObj = YearMonth.parse(month);

        // Tạo LocalDateTime cho ngày đầu tiên của tháng
        LocalDateTime startOfMonth = yearMonthObj.atDay(1).atStartOfDay();

        // Tạo LocalDateTime cho ngày cuối cùng của tháng
        LocalDateTime endOfMonth = yearMonthObj.atEndOfMonth().atTime(23, 59, 59);

        // Gọi phương thức find subscription bằng advisor ID và Subscription date nằm trong khoảng start-end từ repository
        return subscriptionRepository.findByAdvisor_AdvisorIDAndSubscriptionDateBetweenOrderBySubscriptionDateDesc(advisorID, startOfMonth, endOfMonth);
    }

    @Override
    public AdvisorSubscription createSubscriptionTransaction(CreateSubscriptionTransactionRequest createRequest, Integer accountID) {
        // Tìm member băng accountID
        Member member = memberRepository.findByAccount_AccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find member!"));

        // Tạo member transaction
        Transaction memberTrasaction = new Transaction(
                createRequest.getTransactionRequest(),
                member.getMemberID());

        // nhận transaction sao khi lưu xuống database
        Transaction transaction = transactionRepository.save(memberTrasaction);

        // lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();

        // Tạo ngày bắt đầu package
        // nếu endPackage không còn hiệu lực startPackage = currentDate
        // ngược lại startDateOfPackage bằng enDateOfPackage + 1
        LocalDate startDateOfPackage = currentDate;

        // kiểm tra và cập nhật endDateOfPackage của member
        LocalDate endDateOfPackage = member.getEndDateOfPlan();
        int packageDuration = createRequest.getSubscriptionRequest().getPackageDuration();

        if (endDateOfPackage == null || endDateOfPackage.isBefore(currentDate)) {
            // nếu ngày kết thúc không tồn tại hoặc ngày kết thúc bé hơn ngày hiện tại
            // => lấy currentDate + cho PackageDuration của new package
            endDateOfPackage = currentDate.plusDays(packageDuration);
        } else {
            // ngược lại package vẫn còn hiệu lực 
            // start package = ngày sau của endpackage
            //=> cộng thêm package duaration vào startDateOfPackage
            startDateOfPackage = endDateOfPackage.plusDays(1);
            endDateOfPackage = startDateOfPackage.plusDays(packageDuration);
        }

        // tìm advisor
        Advisor advisor = advisorRepository.findById(createRequest.getSubscriptionRequest().getAdvisorID())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find advisor!"));

        // lấy commission rate từ file text
        CommissionRateUtils commissionRateUtils = new CommissionRateUtils();
        Float commissionRate = commissionRateUtils.getCommissionRate();

        // tạo subscription kiểm tra trạng thái status
        AdvisorSubscription subscription
                = subscriptionRepository.save(new AdvisorSubscription(
                        createRequest.getSubscriptionRequest(),
                        startDateOfPackage,
                        endDateOfPackage,
                        member,
                        advisor,
                        transaction.getTransactionID(),
                        commissionRate * 100
                ));
        String[] milestoneLabels = {"25%", "50%", "75%", "100%"};

        // tính giá tiền cho mốc, mỗi mốc bằng 25% tổng tiền  * tỉ lệ phân chia
        BigDecimal amount = createRequest.getSubscriptionRequest()
                .getAmount().multiply(BigDecimal.valueOf(commissionRate * 0.25));

        for (int i = 0; i < milestoneLabels.length; i++) {
            double milestonePercentage = i * 0.25;
            LocalDate milestoneDate = startDateOfPackage.plusDays((long) (packageDuration * milestonePercentage));

            // Tìm commission bằng ngày dự kiến và advisorID
            LocalDate expectedPaymentDate = dateTimeUtils.calculateExpectedPaymentDate(milestoneDate);

            Commission commission = commissionRepository.
                    findByAdvisor_AdvisorIDAndExpectedPaymentDate(
                            advisor.getAdvisorID(),
                            expectedPaymentDate)
                    .orElseThrow(() -> new DuplicateRecordException("Commission is not unique date!"));

            // kiểm tra kết quả
            if (commission == null) {
                commission = new Commission(
                        amount,
                        null,
                        expectedPaymentDate,
                        BigDecimal.ZERO,
                        EPaymentStatus.UNPAID,
                        null,
                        advisor);
            } else {
                // nếu tồn tại cập nhật 
                // giá tiền của commission + giá tiền hiện tại
                BigDecimal totalAmount = commission.getCommissionAmount().add(amount);

                commission.setCommissionAmount(totalAmount);
            }
            Commission commissionSaved = commissionRepository.save(commission);

            // Tạo description cho CommissionAllocation
            String description = String.format("Commission for %s milestone: %s earned for subscription #%s from %s to %s.",
                    milestoneLabels[i], amount.toString(), subscription.getSubscriptionNumber(), subscription.getStartDate(), subscription.getEndDate());

            CommissionAllocation allocation = new CommissionAllocation(
                    amount,
                    description,
                    milestoneLabels[i],
                    milestoneDate,
                    commissionSaved,
                    subscription);
            commissionAllocationRepository.save(allocation);
        }

        // cập nhật số lần sử dụng cho package
        int packageID = createRequest.getSubscriptionRequest().getPackageID();

        Package packageService = packageRepository.findById(packageID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find package with id{" + packageID + "}"));

        int numberOfUses = packageService.getNumberOfUses() + 1;

        packageService.setNumberOfUses(numberOfUses);
        // cập nhật tổng số subscription của advisor
        int totalSubscription = 1 + advisor.getTotalSubscription();
        advisor.setTotalSubscription(totalSubscription);
        advisorRepository.save(advisor);

        // cập nhật ngày kết thúc cho Member
        member.setEndDateOfPlan(endDateOfPackage);
        memberRepository.save(member);

        // cập nhật lại package
        packageRepository.save(packageService);

        return subscription;
    }

    @Override
    public List<AdvisorSubscription> getSubscriptionByMemberAdvisor(Integer accountID) {
        // Tim advisor từ accountID
        Advisor advisor = advisorRepository.findByAccount_AccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find advisor!"));

        // Tìm subscription từ account ID với sắp xếp mới nhất
        return subscriptionRepository.findByAdvisor_AdvisorIDOrderBySubscriptionDateDesc(advisor.getAdvisorID());
    }

    @Override
    public List<Member> getCurrentMemeberOfAdvisor(Integer accountID) {
        // lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();

        // gợi Subscription repository tìm danh sách Subscription bằng accountID và endDate > currentDate
        List<AdvisorSubscription> subscriptions = subscriptionRepository.findByAdvisor_Account_AccountIDAndEndDateGreaterThan(accountID, currentDate);

        // chuyển đổi từ subscription sang List<Member> bằng stream(), distinct đảm bảo không trùng Member
        return subscriptions.stream().map(AdvisorSubscription::getMember).distinct()
                .collect(Collectors.toList());
    }

    @Override
    public AdvisorDetailsResponse getAdvisorOfMember(Integer accountID) {
        Optional<AdvisorSubscription> subscription = subscriptionRepository.findByMember_Account_AccountIDAndSubscriptionStatus(accountID, ESubscriptionStatus.PENDING);

        if (subscription.isPresent()) {
            // tạo advisor response
            int totalMenu = advisorRepository.countMenusByAdvisorID(subscription.get().getAdvisor().getAdvisorID());
            int totalWorkout = advisorRepository.countWorkoutsByAdvisorID(subscription.get().getAdvisor().getAdvisorID());

            AdvisorDetailsResponse response = new AdvisorDetailsResponse(
                    subscription.get().getAdvisor(),
                    totalMenu,
                    totalWorkout);
            return response;
        } else {
            return null;
        }
    }

    @Override
    public void updateSubscriptionStatus() {
        LocalDate today = LocalDate.now();

        // Cập nhật trạng thái của các subscription hết hạn thành "FINISHED"
        subscriptionRepository.updateExpiredSubscriptions(today);

        // Cập nhật trạng thái của các subscription mới bắt đầu vào hôm nay thành "PENDING"
        subscriptionRepository.updatePendingSubscriptions(today);
    }

    @Override
    public List<AdvisorSubscriptionSummary> getAdvisorSubscriptionSummaryByMonth() {
        // tạo danh sách advisorSubscriptionSummary
        List<AdvisorSubscriptionSummary> advisorSubscriptionSummarys = new ArrayList<>();

        // lấy danh sách advisor isActive = true
        List<Advisor> advisors = advisorRepository.findAllByIsActiveTrue();

        // lấy tất cả các commission trước ngày hiện tại trong vòng 6 tháng
        LocalDate startDate = LocalDate.now().minusMonths(6).withDayOfMonth(1);
        LocalDate endDate = LocalDate.now();
        //Tìm ngày đầu tiên và ngày cuối cùng của tháng
        LocalDateTime startDateTime = startDate.atTime(LocalTime.MIN);

        // chuyển đổi từ LocalDate sang LocalDateTime cho trùng datatype dưới database 
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        advisors.forEach(advisor -> {
            List<AdvisorSubscription> subscriptions = subscriptionRepository.findByAdvisor_AdvisorIDAndSubscriptionDateBetweenOrderBySubscriptionDateDesc(
                    advisor.getAdvisorID(), startDateTime, endDateTime);

            // duyệt subscriptions tạo  List<SubscriptionSummaryResponse>
            List<SubscriptionSummaryResponse> subscriptionSummaryResponses = calculateMonthlySubscriptionTotal(subscriptions);

            // add value vào advisorSubscriptionSummarys
            advisorSubscriptionSummarys.add(new AdvisorSubscriptionSummary(
                    advisor.getAdvisorID(),
                    subscriptionSummaryResponses));
        });

        // gọi repository tìm AdvisorSubscriptionSummary
        return advisorSubscriptionSummarys;
    }

    public static List<SubscriptionSummaryResponse> calculateMonthlySubscriptionTotal(List<AdvisorSubscription> subscriptions) {

        // Nhóm các subscription theo YearMonth và tính tổng số subscription cho mỗi nhóm
        Map<YearMonth, Long> monthlySubscriptionCounts = subscriptions.stream()
                .collect(Collectors.groupingBy(
                        subscription -> YearMonth.from(subscription.getSubscriptionDate()),
                        Collectors.counting()
                ));

        // Chuyển đổi Map thành List<MonthlySubscriptionTotal>
        return monthlySubscriptionCounts.entrySet().stream()
                .map(entry -> new SubscriptionSummaryResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CountSubscriptionResponse> countTotalSubscriptionIn6Months() {
        // lấy tất cả các Subscription trước ngày hiện tại trong vòng 6 tháng
        LocalDate startDate = LocalDate.now().minusMonths(6).withDayOfMonth(1);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = LocalDateTime.now();

        return subscriptionRepository.countTotalSubscriptionPerMonthInBetween(startDateTime, endDateTime);
    }
}
