/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.util;

import com.fu.bmi_tracker.model.entities.WorkoutExercise;
import com.fu.bmi_tracker.payload.response.WorkoutExerciseResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Duc Huy
 */
public class WorkoutExerciseConverter {

    public static WorkoutExerciseResponse convertToWorkoutExerciseResponse(WorkoutExercise workoutExercise) {
        WorkoutExerciseResponse workoutExerciseResponse = new WorkoutExerciseResponse(workoutExercise);
        return workoutExerciseResponse;
    }

    public static List<WorkoutExerciseResponse> convertToTagResponseList(List<WorkoutExercise> workoutExercises) {
        return workoutExercises.stream()
                .map(WorkoutExerciseConverter::convertToWorkoutExerciseResponse)
                .collect(Collectors.toList());
    }
}
