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

/**
 *
 * @author Duc Huy
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Workout")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WorkoutID")
    private Integer workoutID;

    @Column(name = "WorkoutName")
    private String workoutName;

    @Column(name = "WorkoutDescription")
    private String workoutDescription;

    @Column(name = "TotalCloriesBurned")
    private Integer totalCloriesBurned;

    @Column(name = "IsActive")
    private Boolean isActive;

    @Column(name = "AdvisorID")
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
