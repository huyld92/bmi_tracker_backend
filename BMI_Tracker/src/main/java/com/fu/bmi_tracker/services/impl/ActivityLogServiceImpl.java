/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.ActivityLog;
import com.fu.bmi_tracker.repository.ActivityLogRepository;
import com.fu.bmi_tracker.services.ActivityLogService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {

    @Autowired
    ActivityLogRepository repository;

    @Override
    public Iterable<ActivityLog> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ActivityLog> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public ActivityLog save(ActivityLog t) {
        return repository.save(t);
    }

    @Override
    public Iterable<ActivityLog> findByRecordID(Integer recordID) {
        return repository.findByRecordID(recordID);
    }

}
