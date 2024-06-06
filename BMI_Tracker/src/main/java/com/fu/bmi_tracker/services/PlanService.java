/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Plan;

/**
 *
 * @author BaoLG
 */
public interface PlanService extends GeneralService<Plan>{
    
    //CREATE
    //Create New Plan
    //public boolean createNewPlan(Plan newPlan);
    
    //READ
    //Find By PlandID
    //public Optional<Plan> findByPlanID (int planID);
    
    //Find ALL
    @Override
    public Iterable<Plan> findAll();
    
    //Find All Plan by AdvisorID
    public Iterable<Plan> findAllPlanByAdvisorID(int advisorID);
    
    //Find All Plan by isActive = true;
    public Iterable<Plan> findAllAvailblePlan();
    
    //UPDATE
    
    //DELETE
    
}
