/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Exercise;
import java.util.List;
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
public class ExerciseEntityResponse {

    private Integer exerciseID;
    private String exerciseName;
    private String emoji;
    private Integer duration;
    private Integer caloriesBurned;
    private Float distance;
    private Boolean isActive;
    private List<TagBasicResponse> tags;

    public ExerciseEntityResponse(Exercise exercise, List<TagBasicResponse> tags) {
        this.exerciseID = exercise.getExerciseID();
        this.exerciseName = exercise.getExerciseName();
        this.emoji = exercise.getEmoji();
        this.duration = exercise.getDuration();
        this.distance = exercise.getDistance();
        this.caloriesBurned = exercise.getCaloriesBurned();
        this.isActive = exercise.getIsActive();
        this.tags = tags;
    }

}
