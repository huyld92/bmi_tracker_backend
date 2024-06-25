/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Workout;
import com.fu.bmi_tracker.payload.request.UpdateWorkoutRequest;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface WorkoutService extends GeneralService<Workout> {

    public Workout updateWorkoutInformation(UpdateWorkoutRequest updateWorkoutRequest);

    public Iterable<Workout> getWorkoutByAdvisorID(Integer advisorID);

    public Workout createNewWorkout(Workout w, List<Integer> tagIDs);

}
