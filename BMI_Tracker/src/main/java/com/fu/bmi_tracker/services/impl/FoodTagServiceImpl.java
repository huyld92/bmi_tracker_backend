/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.FoodTag;
import com.fu.bmi_tracker.repository.FoodTagRepository;
import com.fu.bmi_tracker.services.FoodTagService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodTagServiceImpl implements FoodTagService {

    @Autowired
    FoodTagRepository repository;

    @Override
    public Iterable<FoodTag> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<FoodTag> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public FoodTag save(FoodTag t) {
        return repository.save(t);
    }

    @Override
    public List<FoodTag> saveAll(List<FoodTag> foodTags) {
        return repository.saveAll(foodTags);
    }

}
