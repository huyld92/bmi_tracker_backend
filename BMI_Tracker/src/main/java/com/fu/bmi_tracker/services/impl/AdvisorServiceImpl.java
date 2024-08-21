/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.Commission;
import com.fu.bmi_tracker.payload.response.AdvisorCommissionSummary;
import com.fu.bmi_tracker.payload.response.AdvisorSummaryMenuWorkout;
import com.fu.bmi_tracker.payload.response.CommissionSummary;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.repository.AdvisorRepository;
import com.fu.bmi_tracker.repository.CommissionRepository;
import com.fu.bmi_tracker.repository.MenuRepository;
import com.fu.bmi_tracker.repository.WorkoutRepository;
import com.fu.bmi_tracker.services.AdvisorService;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdvisorServiceImpl implements AdvisorService {

    @Autowired
    private AdvisorRepository advisorRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private CommissionRepository commissionRepository;

    @Override
    public Iterable<Advisor> findAll() {
        return advisorRepository.findAll();
    }

    @Override
    public Optional<Advisor> findById(Integer id) {
        return advisorRepository.findById(id);
    }

    @Override
    public Advisor save(Advisor t) {
        return advisorRepository.save(t);
    }

    @Override
    public Advisor findByAccountID(Integer accountID) {
        return advisorRepository.findByAccount_AccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find advisor with account id{" + accountID + "}!"));

    }

    @Override
    public List<Advisor> findAllAdvisorIsActive() {
        return advisorRepository.findAllByIsActiveTrue();
    }

    @Override
    public List<AdvisorSummaryMenuWorkout> getAdvisorMenuWorkoutSummary() {
        // Tạo AdvisorSummaryMenuWorkout
        List<AdvisorSummaryMenuWorkout> summaryMenuWorkouts = new ArrayList<>();

        // tìm tất cả advisor đang Active
        Iterable<Advisor> advisors = advisorRepository.findAllByIsActiveTrue();

        // kiểm tra kết quả
        if (!advisors.iterator().hasNext()) {
            return summaryMenuWorkouts;
        }

        // Conver advisors thành summaryMenuWorkout
        advisors.forEach(advisor -> {
            // gọi repository của menu và workout đếm số lượng của advisor
            int totalMenu = menuRepository.countByAdvisor_AdvisorIDAndIsActiveTrue(advisor.getAdvisorID());
            int totalWorkout = workoutRepository.countByAdvisor_AdvisorIDAndIsActiveTrue(advisor.getAdvisorID());

            // thêm dữ liệu vào summaryMenuWorkouts
            summaryMenuWorkouts.add(new AdvisorSummaryMenuWorkout(advisor.getAdvisorID(), totalMenu, totalWorkout));
        });

        return summaryMenuWorkouts;
    }

    @Override
    public List<AdvisorCommissionSummary> getAdvisorCommissionSummary() {
        // Tạo AdvisorCommissionSummary
        List<AdvisorCommissionSummary> commissionSummarys = new ArrayList<>();

        // tìm tất cả advisor đang Active
        Iterable<Advisor> advisors = advisorRepository.findAllByIsActiveTrue();

        // kiểm tra kết quả
        if (!advisors.iterator().hasNext()) {
            return commissionSummarys;
        }
        advisors.forEach(advisor -> {
            // lấy tất cả các commission trước ngày hiện tại trong vòng 6 tháng
            LocalDate startDate = LocalDate.now().minusMonths(6).withDayOfMonth(1);
            LocalDate endDate = LocalDate.now();

            List<Commission> commissions = commissionRepository.findByAdvisor_AdvisorIDAndExpectedPaymentDateBetweenOrderByExpectedPaymentDateDesc(advisor.getAdvisorID(), startDate, endDate);
            // Nhóm và tính tổng CommissionAmount theo từng tháng cho mỗi Advisor
            List<CommissionSummary> summarys = calculateMonthlyTotal(commissions);

            commissionSummarys.add(new AdvisorCommissionSummary(advisor.getAdvisorID(), summarys));
        });
        return commissionSummarys;
    }

    public List<CommissionSummary> calculateMonthlyTotal(List<Commission> commissions) {
        Map<YearMonth, BigDecimal> monthlyTotals = commissions.stream()
                .collect(Collectors.groupingBy(
                        commission -> YearMonth.from(commission.getExpectedPaymentDate()),
                        Collectors.reducing(BigDecimal.ZERO, Commission::getCommissionAmount, BigDecimal::add)
                ));

        return monthlyTotals.entrySet().stream()
                .map(entry -> new CommissionSummary(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public Long countTotalAdvisor() {
        return advisorRepository.count();
    }
}
