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
import java.time.LocalDate;
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

    @Column(name = "DefaultCalories", nullable = false)
    private Integer defaultCalories;

    @Column(name = "LastUpdatedTime", nullable = false)
    private LocalDate lastUpdatedTime;

    @Column(name = "DietaryPreference", nullable = false)
    private String dietaryPreference;

    @Column(name = "EndDateOfPlan", nullable = true)
    private LocalDate endDateOfPlan;

    @ManyToOne
    @JoinColumn(name = "ActivityLevelID", nullable = false)
    private ActivityLevel activityLevel;

    public Member(Account account, Integer targetWeight, Double tdee, Double bmr,
            Integer defaultCalories, LocalDate lastUpdatedTime,
            String dietaryPreferenceName, ActivityLevel activityLevel) {
        this.account = account;
        this.targetWeight = targetWeight;
        this.tdee = tdee;
        this.defaultCalories = defaultCalories;
        this.lastUpdatedTime = lastUpdatedTime;
        this.dietaryPreference = dietaryPreferenceName;
        this.activityLevel = activityLevel;
    }

    public Member(Account account, Integer targetWeight, Double tdee,
            Integer defaultCalories, LocalDate lastUpdatedTime,
            String dietaryPreferenceName, ActivityLevel activityLevel) {
        this.account = account;
        this.targetWeight = targetWeight;
        this.tdee = tdee;
        this.defaultCalories = defaultCalories;
        this.lastUpdatedTime = lastUpdatedTime;
        this.dietaryPreference = dietaryPreferenceName;
        this.activityLevel = activityLevel;
    }

    public Member(Integer memberID) {
        this.memberID = memberID;
    }
}
