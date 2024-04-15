/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Goal;
import com.fu.bmi_tracker.repository.GoalRepository;
import com.fu.bmi_tracker.services.GoalService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    GoalRepository repository;

    @Override
    public Iterable<Goal> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Goal> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Goal save(Goal t) {
        return repository.save(t);
    }

}
