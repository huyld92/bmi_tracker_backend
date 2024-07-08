/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author BaoLG
 */
public interface PlanRepository extends JpaRepository<Plan, Integer>{
    
    public Iterable<Plan> findByAdvisor_AdvisorID(int advisorID);
    
    public Iterable<Plan> findByIsActiveTrue();
}
