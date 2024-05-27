/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.WorkoutExercise;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.repository.WorkoutExerciseRepository;
import com.fu.bmi_tracker.services.WorkoutExerciseService;

@Service
public class WorkoutExcerciseServiceImpl implements WorkoutExerciseService {

    @Autowired
    WorkoutExerciseRepository repository;

    @Override
    public Iterable<WorkoutExercise> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<WorkoutExercise> findById(Integer id) {
//        return repository.findById(id);
        return null;
    }

    @Override
    public WorkoutExercise save(WorkoutExercise t) {
        return repository.save(t);
    }

    @Override
    public List<WorkoutExercise> saveAll(List<WorkoutExercise> workoutExercises) {
        return repository.saveAll(workoutExercises);
    }

}
