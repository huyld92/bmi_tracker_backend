/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.WorkoutExercise;
import com.fu.bmi_tracker.payload.request.CreateWorkoutExerciseRequest;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface WorkoutExerciseService extends GeneralService<WorkoutExercise> {

    public List<WorkoutExercise> saveAll(List<WorkoutExercise> workoutExercises);

    public void deleteWorkoutExercise(Integer workoutExerciseID);

    public WorkoutExercise createWorkoutExercise(CreateWorkoutExerciseRequest workoutExerciseRequest);

    public void deactivateWorkoutExercise(Integer workoutExerciseID);

    public void activateWorkoutExercise(Integer workoutExerciseID);

}
