/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Exercise;
import com.fu.bmi_tracker.repository.ExerciseRepository;
import com.fu.bmi_tracker.services.ExerciseService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    ExerciseRepository repository;

    @Override
    public Iterable<Exercise> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Exercise> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Exercise save(Exercise t) {
        return repository.save(t);
    }

    @Override
    public List<Exercise> findByExerciseIDIn(List<Integer> exerciseIDs) {
        return repository.findByExerciseIDIn(exerciseIDs);
    }

}
