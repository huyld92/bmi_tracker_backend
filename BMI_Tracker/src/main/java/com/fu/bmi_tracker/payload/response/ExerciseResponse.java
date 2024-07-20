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
    private String exercisePhoto;
    private String exerciseVideo;
    private Float met;
    private String exerciseDescription;
    private Integer tagID;
    private String tagName;
    private Boolean isActive;

    public ExerciseResponse(Exercise exercise) {
        this.exerciseID = exercise.getExerciseID();
        this.exerciseName = exercise.getExerciseName();
        this.exercisePhoto = exercise.getExercisePhoto();
        this.exerciseVideo = exercise.getExerciseVideo();
        this.met = exercise.getMet();
        this.exerciseDescription = exercise.getExerciseDescription();
        this.tagID = exercise.getTag().getTagID();
        this.tagName = exercise.getTag().getTagName();
        this.isActive = exercise.getIsActive();
    }
}
