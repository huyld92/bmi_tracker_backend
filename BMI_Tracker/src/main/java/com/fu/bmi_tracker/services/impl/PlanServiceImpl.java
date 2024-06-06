/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Plan;
import com.fu.bmi_tracker.repository.PlanRepository;
import com.fu.bmi_tracker.services.PlanService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author BaoLG
 */
@Service
public class PlanServiceImpl implements PlanService{

    @Autowired
    PlanRepository repository;
   
    @Override
    public List<Plan> findAll() {
        return repository.findAll();
    }

    @Override
    public Iterable<Plan> findAllPlanByAdvisorID(int advisorID) {
        return repository.findByAdvisorID(advisorID);
    }

    @Override
    public Iterable<Plan> findAllAvailblePlan() {
        return repository.findByIsActiveTrue();
    }

    @Override
    public Optional<Plan> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Plan save(Plan t) {
       return repository.save(t);
    }    
}
