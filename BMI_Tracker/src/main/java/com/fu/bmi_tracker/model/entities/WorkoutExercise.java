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
@Table(name = "WorkoutExercise")
public class WorkoutExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WorkoutExerciseID", nullable = false)
    private Integer workoutExerciseID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WorkoutID")
    private Workout workout;

    @ManyToOne
    @JoinColumn(name = "ExerciseID")
    private Exercise exercise;

    @Column(name = "Duration", nullable = false)
    private Integer duration;

    @Column(name = "CaloriesBurned", nullable = false)
    private Integer caloriesBurned;

    public WorkoutExercise(Workout workout, Exercise exercise, Integer duration, Integer caloriesBurned) {
        this.workout = workout;
        this.exercise = exercise;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
    }

}
