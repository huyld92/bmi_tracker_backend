/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.WorkoutHistory;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface WorkoutHistoryService extends GeneralService<WorkoutHistory> {

    public Iterable<WorkoutHistory> getWorkoutHistoryOfMember(Integer memberID);

    public WorkoutHistory assignWorkoutToMember(Integer workoutID, Integer memberID);

    public List<String> getMemberNameUsingWorkout(Integer workoutID);

}
