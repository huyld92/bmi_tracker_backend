/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.util;

import com.fu.bmi_tracker.model.entities.WorkoutExercise;
import com.fu.bmi_tracker.payload.response.ExerciseResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Duc Huy
 */
public class ExerciseConverter {

    public static ExerciseResponse convertToWorkoutExerciseResponse(WorkoutExercise workoutExercise) {
        ExerciseResponse workoutExerciseResponse = new ExerciseResponse(workoutExercise.getExercise());
        return workoutExerciseResponse;
    }

    public static List<ExerciseResponse> convertToWorkoutExerciseResponseList(List<WorkoutExercise> workoutExercises) {
        return workoutExercises.stream()
                .map(ExerciseConverter::convertToWorkoutExerciseResponse)
                .collect(Collectors.toList());
    }
}
