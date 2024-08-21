/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.ActivityLevel;
import com.fu.bmi_tracker.payload.response.ActivityLevelResponse;
import com.fu.bmi_tracker.repository.ActivityLevelRepository;
import com.fu.bmi_tracker.services.ActivityLevelService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityLevelServiceImpl implements ActivityLevelService {

    @Autowired
    private ActivityLevelRepository repository;

    @Override
    public Iterable<ActivityLevel> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ActivityLevel> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public ActivityLevel save(ActivityLevel t) {
        return repository.save(t);
    }

    @Override
    public List<ActivityLevelResponse> findAllActivityLevelsWithDetails() {
        return repository.findAllActivityLevelsWithDetails();
    }

}
