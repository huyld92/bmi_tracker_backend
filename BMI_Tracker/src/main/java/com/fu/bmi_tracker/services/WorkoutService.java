/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Workout;
import com.fu.bmi_tracker.payload.request.CreateWorkoutRequest;
import com.fu.bmi_tracker.payload.request.UpdateWorkoutRequest;
import com.fu.bmi_tracker.payload.response.CountWorkoutResponse;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface WorkoutService extends GeneralService<Workout> {

    public Workout updateWorkoutInformation(UpdateWorkoutRequest updateWorkoutRequest);

    public Iterable<Workout> getWorkoutByAdvisorID(Integer advisorID);

    public List<CountWorkoutResponse> countTotalWorkoutIn6Months();

    public Workout createNewWorkout(CreateWorkoutRequest createWorkoutRequest, Integer accountID);

    public Iterable<Workout> getWorkoutActiveByAdvisorID(Integer advisorID);

}
