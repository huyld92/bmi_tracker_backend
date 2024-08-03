/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.CommissionAllocation;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class CommissionAllocationResponse {

    private Integer commissionAllocationID;
    private BigDecimal amount;
    private String description;
    private String milestone;
    private LocalDate milestoneDate;
    private String subscriptionNumber;

    public CommissionAllocationResponse(CommissionAllocation allocation, String subscriptionNumber) {
        this.commissionAllocationID = allocation.getCommissionAllocationID();
        this.amount = allocation.getAmount();
        this.description = allocation.getDescription();
        this.milestone = allocation.getMilestone();
        this.milestoneDate = allocation.getMilestoneDate();
        this.subscriptionNumber = subscriptionNumber;
    }

}
