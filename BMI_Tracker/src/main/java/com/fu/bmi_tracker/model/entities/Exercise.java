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
@Table(name = "Exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExerciseID", nullable = false)
    private Integer exerciseID;

    @Column(name = "ExerciseName", nullable = false)
    private String exerciseName;

    @Column(name = "Emoji", nullable = true)
    private String emoji;
    
    @Column(name = "Duration", nullable = false)
    private Integer duration;

    @Column(name = "CaloriesBurned", nullable = false)
    private Integer caloriesBurned;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;
}
