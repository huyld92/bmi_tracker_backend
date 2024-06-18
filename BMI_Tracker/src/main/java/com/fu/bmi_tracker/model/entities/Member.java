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
    @Column(name = "MemberID", nullable = false)
    private Integer memberID;

    @ManyToOne
    @JoinColumn(name = "AccountID", nullable = false, unique = true)
    private Account account;

    @Column(name = "TargetWeight", nullable = false)
    private Integer targetWeight;

    @Column(name = "TDEE", nullable = false)
    private Double tdee;

    @Column(name = "BMR", nullable = false)
    private Double bmr;

    @Column(name = "DefaultCalories", nullable = false)
    private Integer defaultCalories;

    @Column(name = "IsPrivate", nullable = false)
    private boolean isPrivate;

    @Column(name = "LastUpdatedTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastUpdatedTime;

//    @Column(name = "DietaryPreferenceID", nullable = false)
//    private Integer dietaryPreferenceID;
    @Column(name = "DietaryPreference", nullable = false)
    private String dietaryPreference;

    @ManyToOne
    @JoinColumn(name = "ActivityLevelID", nullable = false)
    private ActivityLevel activityLevel;

    @Column(name = "MenuID", nullable = true)
    private Integer menuID;

    @Column(name = "WorkoutID", nullable = true)
    private Integer workoutID;

    public Member(Account account, Integer targetWeight, Double tdee, Double bmr,
            Integer defaultCalories, boolean isPrivate, LocalDateTime lastUpdatedTime,
            String dietaryPreferenceName, ActivityLevel activityLevel) {
        this.account = account;
        this.targetWeight = targetWeight;
        this.tdee = tdee;
        this.bmr = bmr;
        this.defaultCalories = defaultCalories;
        this.isPrivate = isPrivate;
        this.lastUpdatedTime = lastUpdatedTime;
        this.dietaryPreference = dietaryPreferenceName;
        this.activityLevel = activityLevel;
    }

    public Member(Account account, Integer targetWeight, Double tdee, Double bmr,
            Integer defaultCalories, boolean isPrivate, LocalDateTime lastUpdatedTime,
            String dietaryPreferenceName, ActivityLevel activityLevel, Integer menuID, Integer workoutID) {
        this.account = account;
        this.targetWeight = targetWeight;
        this.tdee = tdee;
        this.bmr = bmr;
        this.defaultCalories = defaultCalories;
        this.isPrivate = isPrivate;
        this.lastUpdatedTime = lastUpdatedTime;
        this.dietaryPreference = dietaryPreferenceName;
        this.activityLevel = activityLevel;
        this.menuID = menuID;
        this.workoutID = workoutID;
    }

    public Member(Integer memberID) {
        this.memberID = memberID;
    }

}
