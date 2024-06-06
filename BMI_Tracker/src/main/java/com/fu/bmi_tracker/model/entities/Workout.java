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
@Table(name = "Workout")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WorkoutID", nullable = false)
    private Integer workoutID;

    @Column(name = "WorkoutName", nullable = false)
    private String workoutName;

    @Column(name = "WorkoutDescription", nullable = false)
    private String workoutDescription;

    @Column(name = "TotalCloriesBurned", nullable = false)
    private Integer totalCloriesBurned;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    @Column(name = "AdvisorID", nullable = false)
    private Integer advisorID;

    //Create new workout
    public Workout(String workoutName, String workoutDescription, Integer totalCloriesBurned, Integer advisorID) {
        this.workoutName = workoutName;
        this.workoutDescription = workoutDescription;
        this.totalCloriesBurned = totalCloriesBurned;
        this.advisorID = advisorID;
        this.isActive = true;
    }

}
