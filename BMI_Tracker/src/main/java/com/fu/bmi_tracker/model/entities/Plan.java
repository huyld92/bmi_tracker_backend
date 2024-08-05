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
@Table(name = "[Plan]")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlanID", nullable = false)
    private Integer planID;

    @Column(name = "PlanCode", nullable = false)
    private String planCode;

    @Column(name = "PlanName", nullable = false)
    private String planName;

    @Column(name = "Price", nullable = false)
    private BigDecimal price;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "PlanDuration", nullable = false)
    private Integer planDuration;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AdvisorID", nullable = false)
    private Advisor advisor;

    @Column(name = "NumberOfUses", nullable = false)
    private Integer numberOfUses;

    @Column(name = "PlanStatus", nullable = false)
    private String planStatus;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    public Plan(String planName, BigDecimal price, String planCode, String description,
            Integer planDuration, Integer advisorID, String planStatus, Boolean isActive, Integer numberOfUses) {
        this.planName = planName;
        this.planCode = planCode;
        this.price = price;
        this.description = description;
        this.planDuration = planDuration;
        this.advisor = new Advisor(advisorID);
        this.isActive = isActive;
        this.planStatus = planStatus;
        this.numberOfUses = numberOfUses;
    }
}
