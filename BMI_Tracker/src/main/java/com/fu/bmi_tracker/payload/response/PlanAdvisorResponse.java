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
public class PlanAdvisorResponse {

    private Integer planID;
    private String planCode;
    private String planName;
    private BigDecimal price;
    private String description;
    private Integer planDuration;
    private Integer advisorID;
    private String fullName;
    private Integer numberOfUses;
    private String planStatus;
    private Boolean isActive;

    public PlanAdvisorResponse(Plan plan) {
        this.planID = plan.getPlanID();
        this.planCode = plan.getPlanCode();
        this.planName = plan.getPlanName();
        this.price = plan.getPrice();
        this.description = plan.getDescription();
        this.planDuration = plan.getPlanDuration();
        this.advisorID = plan.getAdvisor().getAdvisorID();
        this.fullName = plan.getAdvisor().getAccount().getFullName();
        this.numberOfUses = plan.getNumberOfUses();
        this.planStatus = plan.getPlanStatus();
        this.isActive = plan.getIsActive();
    }

}
