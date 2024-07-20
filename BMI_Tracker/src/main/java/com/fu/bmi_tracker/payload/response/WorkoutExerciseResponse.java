/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

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

    private Integer workoutExerciseID;
    private Integer exerciseID;
    private String exerciseName;
    private String exercisePhoto;
    private String exerciseVideo;
    private Float met;
    private String exerciseDescription;
    private String tagName;
    private Integer duration;
    private Integer caloriesBurned;
    private Boolean isActive;

    public WorkoutExerciseResponse(WorkoutExercise workoutExercise) {
        this.workoutExerciseID = workoutExercise.getWorkoutExerciseID();
        this.exerciseID = workoutExercise.getExercise().getExerciseID();
        this.exerciseName = workoutExercise.getExercise().getExerciseName();
        this.exercisePhoto = workoutExercise.getExercise().getExercisePhoto();
        this.exerciseVideo = workoutExercise.getExercise().getExerciseVideo();
        this.met = workoutExercise.getExercise().getMet();
        this.exerciseDescription = workoutExercise.getExercise().getExerciseDescription();
        this.tagName = workoutExercise.getExercise().getTag().getTagName();
        this.duration = workoutExercise.getDuration();
        this.caloriesBurned = workoutExercise.getCaloriesBurned();
        this.isActive = workoutExercise.getIsActive();
    }

}
