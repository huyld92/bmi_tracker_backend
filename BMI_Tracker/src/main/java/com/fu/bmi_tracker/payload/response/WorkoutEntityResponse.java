/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Workout;
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
public class WorkoutEntityResponse {

    private Integer workoutID;
    private String workoutName;
    private String workoutDescription;
    private Integer totalCaloriesBurned;
    private Boolean isActive;
    private Integer standardWeight;
    private Integer advisorID;
    private String fullName;

    public WorkoutEntityResponse(Workout workout) {
        this.workoutID = workout.getWorkoutID();
        this.workoutName = workout.getWorkoutName();
        this.workoutDescription = workout.getWorkoutDescription();
        this.totalCaloriesBurned = workout.getTotalCaloriesBurned();
        this.standardWeight = workout.getStandardWeight();
        this.isActive = workout.getIsActive();
        this.advisorID = workout.getAdvisor().getAdvisorID();
        this.fullName = workout.getAdvisor().getAccount().getFullName();
    }

}
