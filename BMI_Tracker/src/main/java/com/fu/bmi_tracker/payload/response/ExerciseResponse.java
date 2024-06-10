/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Exercise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Duc Huy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseResponse {

    private Integer exerciseID;
    private String exerciseName;
    private String emoji;
    private Integer duration;
    private Integer caloriesBurned;
    private Boolean isActive;

    public ExerciseResponse(Exercise exercise) {
        this.exerciseID = exercise.getExerciseID();
        this.exerciseName = exercise.getExerciseName();
        this.emoji = exercise.getEmoji();
        this.duration = exercise.getDuration();
        this.caloriesBurned = exercise.getCaloriesBurned();
        this.isActive = exercise.getIsActive();
    }
}
