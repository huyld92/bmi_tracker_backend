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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.Instant;
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
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID")
    private int customerID;

    @Column(name = "AccountID")
    private int accountID;

    @Column(name = "GoalID")
    private int goalID;

    @Column(name = "TargetWeight")
    private int targetWeight;

    @Column(name = "TDEE")
    private int tdee;

    @Column(name = "Calories")
    private int calories;

    @Column(name = "IsPrivate")
    private boolean isPrivate;

    @Column(name = "LastUpdatedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Instant lastUpdatedTime;

    @ManyToOne
    @JoinColumn(name = "DietaryPreferenceID")
    private DietaryPreference dietaryPreference;

    public Customer(int accountID, int goalID, int targetWeight, int tdee, int calories, boolean isPrivate, Instant lastUpdatedTime, DietaryPreference dietaryPreference) {
        this.accountID = accountID;
        this.goalID = goalID;
        this.targetWeight = targetWeight;
        this.tdee = tdee;
        this.calories = calories;
        this.isPrivate = isPrivate;
        this.lastUpdatedTime = lastUpdatedTime;
        this.dietaryPreference = dietaryPreference;
    }

    public Customer(int customerID) {
        this.customerID = customerID;
    }

}
