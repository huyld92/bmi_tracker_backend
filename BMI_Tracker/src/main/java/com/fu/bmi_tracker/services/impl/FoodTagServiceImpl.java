/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.TagFood;
import com.fu.bmi_tracker.services.FoodTagService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.repository.TagFoodRepository;

@Service
public class FoodTagServiceImpl implements FoodTagService {

    @Autowired
    TagFoodRepository repository;

    @Override
    public Iterable<TagFood> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<TagFood> findById(Integer id) {
        // return repository.findById(id);
        return null;
    }

    @Override
    public TagFood save(TagFood t) {
        return repository.save(t);
    }

    @Override
    public List<TagFood> saveAll(List<TagFood> foodTags) {
        return repository.saveAll(foodTags);
    }

}
