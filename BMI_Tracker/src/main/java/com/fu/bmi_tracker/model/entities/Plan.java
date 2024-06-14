/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

/**
 *
 * @author Duc Huy
 */
@Entity
@SQLRestriction(value = "IsActive = 1")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "[Plan]")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlanID", nullable = false)
    private Integer planID;

    @Column(name = "PlanName", nullable = false)
    private String planName;

    @Column(name = "Price", nullable = false)
    private BigDecimal price;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "PlanDuration", nullable = false)
    private Integer planDuration;

    @Column(name = "AdvisorID", nullable = false)
    private Integer advisorID;

    @Column(name = "IsPopular", nullable = false)
    private boolean isPopular;

    @Column(name = "IsActive", nullable = false)
    private boolean isActive;

    public Plan(String planName, BigDecimal price, String description,
            Integer planDuration, Integer advisorID, boolean isActive, boolean isPopular) {
        this.planName = planName;
        this.price = price;
        this.description = description;
        this.planDuration = planDuration;
        this.advisorID = advisorID;
        this.isActive = isActive;
        this.isPopular = isPopular;
    }
}
