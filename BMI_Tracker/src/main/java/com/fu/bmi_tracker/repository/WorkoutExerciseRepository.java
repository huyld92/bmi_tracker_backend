/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.WorkoutExercise;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Integer> {

    public List<WorkoutExercise> findByWorkout_WorkoutIDAndIsActiveTrue(Integer workoutID);

}
