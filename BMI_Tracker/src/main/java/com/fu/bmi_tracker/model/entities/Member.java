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
@Table(name = "[Member]")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MemberID")
    private Integer memberID;

    @Column(name = "AccountID")
    private Integer accountID;

    @Column(name = "TargetWeight")
    private Integer targetWeight;

    @Column(name = "TDEE")
    private Double tdee;

    @Column(name = "BMR")
    private Double bmr;

    @Column(name = "DefaultCalories")
    private Integer defaultCalories;

    @Column(name = "IsPrivate")
    private boolean isPrivate;

    @Column(name = "LastUpdatedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastUpdatedTime;

    @Column(name = "DietaryPreferenceID")
    private Integer dietaryPreferenceID;

    @ManyToOne
    @JoinColumn(name = "ActivityLevelID")
    private ActivityLevel activityLevel;

    @Column(name = "MenuID")
    private Integer menuID;

    @Column(name = "WorkoutID")
    private Integer workoutID;

//    public Member(Integer accountID, Integer targetWeight, Double tdee, Double bmr,
//            Integer defaultCalories, boolean isPrivate, LocalDateTime lastUpdatedTime,
//            Integer dietaryPreferenceID, ActivityLevel activityLevel) {
//        this.accountID = accountID;
//        this.targetWeight = targetWeight;
//        this.tdee = tdee;
//        this.bmr = bmr;
//        this.defaultCalories = defaultCalories;
//        this.isPrivate = false;
//        this.lastUpdatedTime = lastUpdatedTime;
//        this.dietaryPreferenceID = dietaryPreferenceID;
//        this.activityLevel = activityLevel;
//    }
    public Member(Integer accountID, Integer targetWeight, Double tdee, Double bmr,
            Integer defaultCalories, boolean isPrivate, LocalDateTime lastUpdatedTime,
            Integer dietaryPreferenceID, ActivityLevel activityLevel) {
        this.accountID = accountID;
        this.targetWeight = targetWeight;
        this.tdee = tdee;
        this.bmr = bmr;
        this.defaultCalories = defaultCalories;
        this.isPrivate = isPrivate;
        this.lastUpdatedTime = lastUpdatedTime;
        this.dietaryPreferenceID = dietaryPreferenceID;
        this.activityLevel = activityLevel;
    }

    public Member(Integer accountID, Integer targetWeight, Double tdee, Double bmr,
            Integer defaultCalories, boolean isPrivate, LocalDateTime lastUpdatedTime,
            Integer dietaryPreferenceID, ActivityLevel activityLevel, Integer menuID, Integer workoutID) {
        this.accountID = accountID;
        this.targetWeight = targetWeight;
        this.tdee = tdee;
        this.bmr = bmr;
        this.defaultCalories = defaultCalories;
        this.isPrivate = isPrivate;
        this.lastUpdatedTime = lastUpdatedTime;
        this.dietaryPreferenceID = dietaryPreferenceID;
        this.activityLevel = activityLevel;
        this.menuID = menuID;
        this.workoutID = workoutID;
    }

    public Member(Integer memberID) {
        this.memberID = memberID;
    }

}
