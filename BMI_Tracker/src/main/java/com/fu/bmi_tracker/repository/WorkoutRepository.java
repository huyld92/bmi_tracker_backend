/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Workout;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Integer> {

    public Iterable<Workout> findByAdvisorID(Integer advisorID);

    @Query("SELECT w FROM Workout w"
            + " JOIN TagWorkout tw ON w.workoutID = tw.workout.workoutID"
            + " JOIN Tag t ON tw.tag.tagID = t.tagID"
            + " WHERE t.tagName = :tagName AND w.advisorID = 3"
            + " ORDER BY w.workoutID ASC")
    Workout findFirstByTagName(String tagName);

}
