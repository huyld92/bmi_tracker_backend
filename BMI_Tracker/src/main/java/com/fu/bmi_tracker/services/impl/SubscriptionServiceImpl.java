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
import jakarta.transaction.Transactional;
import java.math.RoundingMode;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AdvisorRepository advisorRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CommissionRepository commissionRepository;

    @Autowired
    private CommissionAllocationRepository commissionAllocationRepository;

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private DateTimeUtils dateTimeUtils;

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

    /**
     * Phương thức này được đánh dấu với annotation `@Transactional`. Điều này
     * đảm bảo rằng tất cả các thao tác trong phương thức sẽ được thực thi trong
     * một giao dịch (transaction). Nếu có bất kỳ lỗi nào xảy ra trong quá trình
     * xử lý (ví dụ: lỗi kết nối cơ sở dữ liệu, lỗi logic, hoặc lỗi ngoại lệ
     * khác), toàn bộ các thay đổi sẽ được rollback (hoàn tác) tự động, đảm bảo
     * tính nhất quán của dữ liệu trong cơ sở dữ liệu.
     *
     * *
     * @param createRequest
     * @param accountID
     * @return
     */
    @Override
    @Transactional
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
            endDateOfPackage = startDateOfPackage.plusDays(packageDuration);
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

        // tạo subscription
        AdvisorSubscription subscription
                = subscriptionRepository.save(
                        new AdvisorSubscription(
                                createRequest.getSubscriptionRequest(),
                                startDateOfPackage,
                                endDateOfPackage,
                                member,
                                advisor,
                                transaction.getTransactionID(),
                                commissionRate * 100
                        )
                );
        // Tạo ra các mốc thời gian (25%, 50%, 75%, 100%)
        String[] milestoneLabels = {"25%", "50%", "75%", "100%"};
        int numberOfMilestones = milestoneLabels.length;

        // Tính số tiền cho mỗi mốc
        BigDecimal milestoneAmount = createRequest.getSubscriptionRequest()
                .getAmount()
                .multiply(BigDecimal.valueOf(commissionRate * 0.25))
                .setScale(2, RoundingMode.HALF_UP);

        // Tạo Map để lưu trữ các ngày thanh toán và gộp milestoneLabels tương ứng
        Map<LocalDate, Map<String, BigDecimal>> paymentDates = new HashMap<>();

        // Gộp các vòng lặp lại để tính toán ngày thanh toán, gộp số tiền và lưu Commission cùng CommissionAllocation
        for (int i = 0; i < numberOfMilestones; i++) {
            double milestonePercentage = (i + 1) * 0.25; // 25%, 50%, 75%, 100%
            LocalDate milestoneDate = startDateOfPackage.plusDays((long) (packageDuration * milestonePercentage));
            LocalDate expectedPaymentDate = dateTimeUtils.calculateExpectedPaymentDate(milestoneDate);

            // Gộp các milestoneLabels và số tiền cho cùng một ngày thanh toán
            paymentDates.computeIfAbsent(expectedPaymentDate, k -> new HashMap<>())
                    .merge(milestoneLabels[i], milestoneAmount, BigDecimal::add);
        }

        // Tạo và lưu trữ Commission và CommissionAllocation
        for (Map.Entry<LocalDate, Map<String, BigDecimal>> entry : paymentDates.entrySet()) {
            LocalDate expectedPaymentDate = entry.getKey();
            Map<String, BigDecimal> milestonesForDate = entry.getValue();

            // Gộp số tiền cho tất cả các mốc thanh toán tại ngày thanh toán
            BigDecimal totalAmountForDate = BigDecimal.ZERO;
            StringBuilder milestonesLabelsDescription = new StringBuilder();

            for (Map.Entry<String, BigDecimal> milestoneEntry : milestonesForDate.entrySet()) {
                String milestoneLabel = milestoneEntry.getKey();
                BigDecimal amount = milestoneEntry.getValue();

                // Cộng dồn số tiền cho ngày thanh toán và xây dựng mô tả
                totalAmountForDate = totalAmountForDate.add(amount).setScale(2, RoundingMode.HALF_UP);
                if (milestonesLabelsDescription.length() > 0) {
                    milestonesLabelsDescription.append(", ");
                }
                milestonesLabelsDescription.append(milestoneLabel);
            }

            // Tìm hoặc tạo mới Commission theo Advisor ID và ExpectedPaymentDate
            Commission commission = commissionRepository
                    .findByAdvisor_AdvisorIDAndExpectedPaymentDate(advisor.getAdvisorID(), expectedPaymentDate)
                    .orElse(new Commission(
                            totalAmountForDate,
                            null,
                            expectedPaymentDate,
                            BigDecimal.ZERO,
                            EPaymentStatus.UNPAID,
                            null,
                            advisor));

            // Cập nhật tổng số tiền cho Commission
            commission.setCommissionAmount(
                    commission.getCommissionAmount()
                            .add(totalAmountForDate)
                            .setScale(2, RoundingMode.HALF_UP));

            // Lưu lại Commission
            Commission commissionSaved = commissionRepository.save(commission);

            // Tạo mô tả cho CommissionAllocation
            String description = String.format(
                    "Commission for %s milestone: %s VND earned for subscription #%s from %s to %s.",
                    milestonesLabelsDescription.toString(), // Gộp các nhãn mốc thành một chuỗi
                    totalAmountForDate.toString(),
                    createRequest.getSubscriptionRequest().getSubscriptionNumber(),
                    startDateOfPackage,
                    endDateOfPackage);

            // Tạo và lưu lại CommissionAllocation
            CommissionAllocation allocation = new CommissionAllocation(
                    totalAmountForDate,
                    description,
                    milestonesLabelsDescription.toString(), // Gán các mốc thanh toán đã gộp
                    expectedPaymentDate, // Ngày dự kiến thanh toán
                    commissionSaved, // Liên kết với Commission đã lưu
                    subscription);  // Liên kết với Subscription

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
        List<AdvisorSubscription> subscriptions
                = subscriptionRepository.
                        findByAdvisor_Account_AccountIDAndSubscriptionStatusAndEndDateGreaterThan(
                                accountID,
                                ESubscriptionStatus.PENDING,
                                currentDate);

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
