/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Workout;
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
public class WorkoutResonse {

    private Integer workoutID;
    private String workoutName;
    private String workoutDescription;
    private Integer totalCaloriesBurned;
    private Integer standardWeight;
    private Boolean isActive;
    private Integer advisorID;
    private String fullName;
    private List<WorkoutExerciseResponse> workoutExercises;
    private List<String> membersUsing;

    public WorkoutResonse(Workout workout, List<WorkoutExerciseResponse> workoutExercisesResponses,List<String> membersUsing) {
        this.workoutID = workout.getWorkoutID();
        this.workoutName = workout.getWorkoutName();
        this.workoutDescription = workout.getWorkoutDescription();
        this.totalCaloriesBurned = workout.getTotalCaloriesBurned();
        this.standardWeight = workout.getStandardWeight();
        this.isActive = workout.getIsActive();
        this.advisorID = workout.getAdvisor().getAdvisorID();
        this.fullName = workout.getAdvisor().getAccount().getFullName();
        this.membersUsing = membersUsing;
        this.workoutExercises = workoutExercisesResponses;
    }

}
