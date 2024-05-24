/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.payload.request.CreateExerciseLogRequest;
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
@Table(name = "ExerciseLog")
public class ExerciseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExerciseLogID")
    private Integer exerciseLogID;

    @Column(name = "ExerciseName")
    private String exerciseName;

    @Column(name = "CaloriesBurned")
    private Integer caloriesBurned;

    @Column(name = "Duaration")
    private Integer duaration;

    @Column(name = "RecordID")
    private Integer recordID;

    public ExerciseLog(CreateExerciseLogRequest exerciseLogRequest) {
        this.exerciseName = exerciseLogRequest.getExerciseName();
        this.caloriesBurned = exerciseLogRequest.getCaloriesBurned();
        this.duaration = exerciseLogRequest.getDuaration();
        this.recordID = exerciseLogRequest.getRecordID();
    }

}
