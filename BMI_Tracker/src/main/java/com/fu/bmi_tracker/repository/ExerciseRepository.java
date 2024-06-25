/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Exercise;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    List<Exercise> findByExerciseIDIn(List<Integer> exerciseIds);

    @Transactional
    @Modifying
    @Query("UPDATE Exercise e SET e.isActive = false WHERE e.exerciseID = :exerciseID")
    public void deactivateById(Integer exerciseID);

    @Query("SELECT e FROM Exercise e WHERE e.exerciseID IN "
            + "(SELECT te.exercise.exerciseID FROM TagExercise te JOIN Tag t ON te.tag.tagID = t.tagID "
            + "WHERE t.tagName = :tagName) AND e.isActive = true")
    public List<Exercise> findExerciseWithTagName(String tagName);

    @Query("SELECT e FROM Exercise e WHERE e.exerciseID NOT IN "
            + "(SELECT te.exercise.exerciseID FROM TagExercise te JOIN Tag t ON te.tag.tagID = t.tagID "
            + "WHERE t.tagName = :tagName) AND e.isActive = true")
    public List<Exercise> findExerciseWithoutTagName(String tagName);

}
