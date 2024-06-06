/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Exercise;
import com.fu.bmi_tracker.model.entities.WorkoutExercise;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface WorkoutExerciseService extends GeneralService<WorkoutExercise> {

    public List<WorkoutExercise> saveAll(List<WorkoutExercise> workoutExercises);

    public List<Exercise> getAllExerciseByWorkoutID(Integer workoutID);

}
