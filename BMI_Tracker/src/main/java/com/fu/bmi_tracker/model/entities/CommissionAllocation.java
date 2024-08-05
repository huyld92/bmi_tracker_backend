/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Duc Huy
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CommissionAllocation")
public class CommissionAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommissionAllocationID", nullable = false)
    private Integer commissionAllocationID;

    @Column(name = "Amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "Milestone", nullable = false)
    private String milestone;

    @Column(name = "MilestoneDate", nullable = false)
    private LocalDate milestoneDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CommissionID")
    private Commission commission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubscriptionID")
    private AdvisorSubscription subscription;

    public CommissionAllocation(BigDecimal amount, String description, String milestone, LocalDate milestoneDate, Commission commission, AdvisorSubscription subscription) {
        this.amount = amount;
        this.description = description;
        this.milestone = milestone;
        this.milestoneDate = milestoneDate;
        this.commission = commission;
        this.subscription = subscription;
    }
 
}
