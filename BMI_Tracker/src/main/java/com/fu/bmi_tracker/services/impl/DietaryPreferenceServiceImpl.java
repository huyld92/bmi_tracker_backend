/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.DietaryPreference;
import com.fu.bmi_tracker.repository.DietaryPreferenceRepository;
import com.fu.bmi_tracker.services.DietaryPreferenceService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DietaryPreferenceServiceImpl implements DietaryPreferenceService {

    @Autowired
    DietaryPreferenceRepository repository;

    @Override
    public Iterable<DietaryPreference> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<DietaryPreference> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public DietaryPreference save(DietaryPreference t) {
        return repository.save(t);
    }

}
