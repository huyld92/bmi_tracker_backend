/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Plan;
import com.fu.bmi_tracker.payload.request.CreatePlanRequest;

/**
 *
 * @author BaoLG
 */
public interface PlanService extends GeneralService<Plan> {

    //Find All Plan by AdvisorID
    public Iterable<Plan> findAllPlanByAdvisorID(int advisorID);

    //Find All Plan by isActive = true;
    public Iterable<Plan> findAllAvailablePlan();

    public Plan createPlan(CreatePlanRequest newPlan, Integer accountID);

    public Iterable<Plan> findAllPlanFromPersonally(Integer accountID);

    public Iterable<Plan> getAllPlanForSubscription(Integer advisorID);
}
