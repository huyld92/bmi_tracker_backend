/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Exercise;
import com.fu.bmi_tracker.payload.request.CreateExerciseRequest;
import com.fu.bmi_tracker.payload.request.UpdateExerciseRequest;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface ExerciseService extends GeneralService<Exercise> {

    public List<Exercise> findByExerciseIDIn(List<Integer> exerciseIDs);

    public Exercise createExercise(CreateExerciseRequest createExerciseRequest);

    public Exercise updateExercise(UpdateExerciseRequest updateExercerRequest);

    public void deactivateExercise(Integer exerciseID);

}
