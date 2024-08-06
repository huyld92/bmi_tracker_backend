/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Plan;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Duc Huy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanResponse {

    private Integer planID;
    private String planName;
    private String planCode;
    private BigDecimal price;
    private String description;
    private Integer planDuration;
    private Integer advisorID;
    private Integer numberOfUses;

    public PlanResponse(Plan plan) {
        this.planID = plan.getPlanID();
        this.planName = plan.getPlanName();
        this.planCode = plan.getPlanCode();
        this.price = plan.getPrice();
        this.description = plan.getDescription();
        this.planDuration = plan.getPlanDuration();
        this.advisorID = plan.getAdvisor().getAdvisorID();
        this.numberOfUses = plan.getNumberOfUses();
    }

}
