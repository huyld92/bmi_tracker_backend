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
    private Integer totalCloriesBurned;
    private Boolean isActive;
    private Integer advisorID;

    public WorkoutEntityResponse(Workout workout) {
        this.workoutID = workout.getWorkoutID();
        this.workoutName = workout.getWorkoutName();
        this.workoutDescription = workout.getWorkoutDescription();
        this.totalCloriesBurned = workout.getAdvisorID();
        this.isActive = workout.getIsActive();
        this.advisorID = workout.getAdvisorID();
    }

}
