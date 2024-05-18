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
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Duc Huy
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "[User]")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private int userID;

    @Column(name = "AccountID")
    private int accountID;

    @Column(name = "GoalID")
    private int goalID;

    @Column(name = "TargetWeight")
    private int targetWeight;

    @Column(name = "TDEE")
    private double tdee;

    @Column(name = "BMR")
    private double bmr;

    @Column(name = "DefaultCalories")
    private int defaultCalories;

    @Column(name = "IsPrivate")
    private boolean isPrivate;

    @Column(name = "LastUpdatedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastUpdatedTime;

    @Column(name = "DietaryPreferenceID")
    private int dietaryPreferenceID;

    @ManyToOne()
    @JoinColumn(name = "ActivityLevelID")
    private ActivityLevel activityLevel;

    public User(int accountID, int goalID, int targetWeight, double tdee, double bmr,
            int defaultCalories, boolean isPrivate, LocalDateTime lastUpdatedTime,
            int dietaryPreferenceID, ActivityLevel activityLevel) {
        this.accountID = accountID;
        this.goalID = goalID;
        this.targetWeight = targetWeight;
        this.tdee = tdee;
        this.bmr = bmr;
        this.defaultCalories = defaultCalories;
        this.isPrivate = false;
        this.lastUpdatedTime = lastUpdatedTime;
        this.dietaryPreferenceID = dietaryPreferenceID;
        this.activityLevel = activityLevel;
    }

    public User(int userID) {
        this.userID = userID;
    }

}
