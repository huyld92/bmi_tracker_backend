/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.payload.response.AdvisorCommissionSummary;
import com.fu.bmi_tracker.payload.response.AdvisorSummaryMenuWorkout;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface AdvisorService extends GeneralService<Advisor> {

    public Advisor findByAccountID(Integer accountID);

    public List<Advisor> findAllAdvisorIsActive();

    public List<AdvisorSummaryMenuWorkout> getAdvisorMenuWorkoutSummary();

    public List<AdvisorCommissionSummary> getAdvisorCommissionSummary();

    public Long countTotalAdvisor();

}
