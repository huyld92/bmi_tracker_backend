/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.Plan;
import com.fu.bmi_tracker.repository.AdvisorRepository;
import com.fu.bmi_tracker.repository.PlanRepository;
import com.fu.bmi_tracker.services.PlanService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author BaoLG
 */
@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    PlanRepository planRepository;

    @Autowired
    AdvisorRepository advisorRepository;

    @Override
    public List<Plan> findAll() {
        return planRepository.findAll();
    }

    @Override
    public Iterable<Plan> findAllPlanByAdvisorID(int advisorID) {
        return planRepository.findByAdvisor_AdvisorID(advisorID);
    }

    @Override
    public Iterable<Plan> findAllAvailblePlan() {
        return planRepository.findByIsActiveTrue();
    }

    @Override
    public Optional<Plan> findById(Integer id) {
        return planRepository.findById(id);
    }

    @Override
    public Plan save(Plan t) {
        return planRepository.save(t);
    }

    @Override
    public Plan createPlan(Plan plan, Integer accountID) {

        // find Advisor
        Advisor advisor = advisorRepository.findByAccount_AccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find advisor!"));
        // set advisor ID
        plan.setAdvisor(advisor);

        return save(plan);
    }

    @Override
    public Iterable<Plan> findAllPlanFromPersonally(Integer accountID) {
        // find Advisor
        Advisor advisor = advisorRepository.findByAccount_AccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find advisor!"));

        // GỌi repository tìm tất cả plan của advisor
        return planRepository.findByAdvisor_AdvisorID(advisor.getAdvisorID());
    }
}
