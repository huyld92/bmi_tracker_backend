/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Exercise;
import com.fu.bmi_tracker.model.entities.WorkoutExercise;
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
public class WorkoutExerciseResponse {

    private Integer exerciseID;
    private String exerciseName;
    private String emoji;
    private Integer duration;
    private Float distance;
    private Integer caloriesBurned;
    private Boolean isActive;

    public WorkoutExerciseResponse(WorkoutExercise workoutExercise) {
        this.exerciseID = workoutExercise.getExercise().getExerciseID();
        this.exerciseName = workoutExercise.getExercise().getExerciseName();
        this.emoji = workoutExercise.getExercise().getEmoji();
        this.duration = workoutExercise.getExercise().getDuration();
        this.distance = workoutExercise.getExercise().getDistance();
        this.caloriesBurned = workoutExercise.getExercise().getCaloriesBurned();
        this.isActive = workoutExercise.getIsActive();
    }

}
