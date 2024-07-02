/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.Commission;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.Transaction;
import com.fu.bmi_tracker.model.entities.Booking;
import com.fu.bmi_tracker.model.entities.Plan;
import com.fu.bmi_tracker.model.enums.EBookingStatus;
import com.fu.bmi_tracker.model.enums.EPaymentStatus;
import com.fu.bmi_tracker.payload.request.CreateBookingTransactionRequest;
import com.fu.bmi_tracker.payload.response.AdvisorDetailsResponse;
import com.fu.bmi_tracker.repository.AdvisorRepository;
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
import com.fu.bmi_tracker.services.BookingService;
import com.fu.bmi_tracker.repository.BookingRepository;
import com.fu.bmi_tracker.repository.PlanRepository;

@Service
public class BookingServiceImpl implements BookingService {
    
    @Autowired
    BookingRepository bookingRepository;
    
    @Autowired
    MemberRepository memberRepository;
    
    @Autowired
    AdvisorRepository advisorRepository;
    
    @Autowired
    TransactionRepository transactionRepository;
    
    @Autowired
    CommissionRepository commissionRepository;
    
    @Autowired
    PlanRepository planRepository;
    
    @Autowired
    DateTimeUtils dateTimeUtils;
    
    @Override
    public Iterable<Booking> findAll() {
        return bookingRepository.findAll();
    }
    
    @Override
    public Optional<Booking> findById(Integer id) {
        return bookingRepository.findById(id);
    }
    
    @Override
    public Booking save(Booking t) {
        return bookingRepository.save(t);
    }
    
    @Override
    public Iterable<Booking> getBookingByMemberAccountID(Integer accountID) {
        return bookingRepository.findByMember_Account_AccountID(accountID);
    }
    
    @Override
    public Iterable<Booking> getBookingByMemberID(Integer memberID) {
        return bookingRepository.findByMemberMemberID(memberID);
        
    }
    
    @Override
    public Iterable<Booking> getBookingByAdvisorIDAndMonth(Integer advisorID, String month) {
        // Chuyển đổi chuỗi "yyyy-MM" thành YearMonth
        YearMonth yearMonthObj = YearMonth.parse(month);

        // Tạo LocalDateTime cho ngày đầu tiên của tháng
        LocalDateTime startOfMonth = yearMonthObj.atDay(1).atStartOfDay();

        // Tạo LocalDateTime cho ngày cuối cùng của tháng
        LocalDateTime endOfMonth = yearMonthObj.atEndOfMonth().atTime(23, 59, 59);

        // Gọi phương thức find booking bằng advisor ID và Booking date nằm trong khoảng start-end từ repository
        return bookingRepository.findByAdvisor_AdvisorIDAndBookingDateBetween(advisorID, startOfMonth, endOfMonth);
    }
    
    @Override
    public Booking createBookingTransaction(CreateBookingTransactionRequest createRequest, Integer accountID) {
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

        // Tạo ngày bắt đầu plan
        // nếu endPlan không còn hiệu lực startPlan = currentDate
        // ngược lại startDateOfPlan bằng enDateOfPlan
        LocalDate startDateOfPlan = currentDate;

        // kiểm tra và cập nhật endDateOfPlan của member
        LocalDate endDateOfPlan = member.getEndDateOfPlan();
        int planDuration = createRequest.getBookingRequest().getPlanDuration();
        
        if (endDateOfPlan == null || endDateOfPlan.isBefore(currentDate)) {
            // nếu ngày kết thúc không tồn tại hoặc ngày kết thúc bé hơn ngày hiện tại
            // => lấy currentDate + cho PlanDuration của new plan
            endDateOfPlan = currentDate.plusDays(planDuration);
        } else {
            // ngược lại plan vẫn còn hiệu lực 
            // start plan = ngày sau của endplan
            //=> cộng thêm plan duaration vào endDateOfPlan
            startDateOfPlan = endDateOfPlan.plusDays(1);
            endDateOfPlan = endDateOfPlan.plusDays(planDuration);
        }

        // cập nhật ngày kết thúc cho Member
        member.setEndDateOfPlan(endDateOfPlan);
        memberRepository.save(member);

        // tìm advisor
        Advisor advisor = advisorRepository.findById(createRequest.getBookingRequest().getAdvisorID())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find advisor!"));

        // tính ngày dự kiến thanh toán 
        // Tìm commission bằng ngày dự kiến và advisorID
        LocalDate expectedPaymentDate = dateTimeUtils.calculateExpectedPaymentDate(endDateOfPlan);
        Commission commission = commissionRepository.
                findByAdvisor_AdvisorIDAndExpectedPaymentDate(
                        advisor.getAdvisorID(),
                        expectedPaymentDate);

        // kiểm tra kết quả
        if (commission == null) {
            // tạo mới commission
            BigDecimal commissionRate = BigDecimal.valueOf(50);
            BigDecimal commissionAmount = createRequest.getBookingRequest()
                    .getAmount()
                    .multiply(commissionRate)
                    .divide(BigDecimal.valueOf(100));
            
            Commission c = new Commission(
                    commissionAmount,
                    50,
                    null,
                    expectedPaymentDate,
                    BigDecimal.ZERO,
                    EPaymentStatus.UNPAID,
                    null,
                    advisor);
            // lưu commission
            commission = commissionRepository.save(c);
        } else {
            BigDecimal commissionRate = BigDecimal.valueOf(commission.getCommissionRate());
            BigDecimal commissionAmount = createRequest.getBookingRequest()
                    .getAmount()
                    .multiply(commissionRate)
                    .divide(BigDecimal.valueOf(100));
            commission.setCommissionAmount(commissionAmount);
            commissionRepository.save(commission);
        }

        // cập nhật số lần sử dụng cho plan
        int planID = createRequest.getBookingRequest().getPlanID();
        Plan plan = planRepository.findById(planID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find plan with id{" + planID + "}"));
        int numberOfUses = plan.getNumberOfUses() + 1;
        plan.setNumberOfUses(numberOfUses);

        // cập nhật lại plan
        planRepository.save(plan);

        // tạo booking kiểm tra trạng thái status
        Booking booking = new Booking(
                createRequest.getBookingRequest(),
                startDateOfPlan,
                endDateOfPlan,
                member.getMemberID(),
                advisor,
                transaction.getTransactionID(),
                commission.getCommissionID()
        );
        
        return bookingRepository.save(booking);
    }
    
    @Override
    public List<Booking> getBookingByMemberAdvisor(Integer accountID) {
        // Tim advisor từ accountID
        Advisor advisor = advisorRepository.findByAccount_AccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find advisor!"));

        // Tìm booking từ account ID với sắp xếp mới nhất
        return bookingRepository.findByAdvisor_AdvisorIDOrderByBookingDateDesc(advisor.getAdvisorID());
    }
    
    @Override
    public List<Member> getCurrentMemeberOfAdvisor(Integer accountID) {
        // lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();

        // gợi Booking repository tìm danh sách Booking bằng accountID và endDate > currentDate
        List<Booking> bookings = bookingRepository.findByAdvisor_Account_AccountIDAndEndDateGreaterThan(accountID, currentDate);

        // chuyển đổi từ booking sang List<Member> bằng stream(), distinct đảm bảo không trùng Member
        return bookings.stream().map(Booking::getMember).distinct()
                .collect(Collectors.toList());
    }
    
    @Override
    public AdvisorDetailsResponse getAdvisorOfMember(Integer accountID) {
        Optional<Booking> booking = bookingRepository.findByMember_Account_AccountIDAndBookingStatus(accountID, EBookingStatus.PENDING);
        
        if (booking.isPresent()) {
            // tạo advisor response
            int totalMenu = advisorRepository.countMenusByAdvisorID(accountID);
            int totalWorkout = advisorRepository.countWorkoutsByAdvisorID(accountID);
            
            AdvisorDetailsResponse response = new AdvisorDetailsResponse(
                    booking.get().getAdvisor(),
                    totalMenu,
                    totalWorkout);
            return response;
        } else {
            return null;
        }
    }
    
}
