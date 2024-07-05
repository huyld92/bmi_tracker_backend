/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.payload.response.AdvisorCommissionSummary;
import com.fu.bmi_tracker.payload.response.AdvisorSummaryMenuWorkout;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class AdvisorServiceImpl implements AdvisorService {

    @Autowired
    AdvisorRepository advisorRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    CommissionRepository commissionRepository;

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
            int totalWorkout = workoutRepository.countByAdvisorIDAndIsActiveTrue(advisor.getAdvisorID());

            // thêm dữ liệu vào summaryMenuWorkouts
            summaryMenuWorkouts.add(new AdvisorSummaryMenuWorkout(advisor, totalMenu, totalWorkout));
        });

        return summaryMenuWorkouts;
    }

    @Override
    public List<AdvisorCommissionSummary> getAdvisorCommissionSummary(LocalDate localDate) {
        // Tạo AdvisorCommissionSummary
        List<AdvisorCommissionSummary> commissionSummarys = new ArrayList<>();

        // tìm tất cả advisor đang Active
        Iterable<Advisor> advisors = advisorRepository.findAllByIsActiveTrue();
        System.out.println("aaaaaaaaaaaaaaaa");
        // kiểm tra kết quả
        if (!advisors.iterator().hasNext()) {
            return commissionSummarys;
        }

        // Conver advisors thành commissionSummarys
        advisors.forEach(advisor -> {
            int month = localDate.getMonthValue();
            int year = localDate.getYear();
            BigDecimal totalComisstionAmount
                    = commissionRepository.getTotalCommissionByAdvisorIdAndMonth(
                            advisor.getAdvisorID(), year, month);
            System.out.println("aaaaaa: " + totalComisstionAmount);
            // thêm dữ liệu vào summaryMenuWorkouts
            commissionSummarys.add(new AdvisorCommissionSummary(advisor, totalComisstionAmount));
        });

        return commissionSummarys;
    }

}
