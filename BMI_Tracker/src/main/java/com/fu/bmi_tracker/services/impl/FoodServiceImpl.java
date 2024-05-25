/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.repository.FoodRepository;
import com.fu.bmi_tracker.services.FoodService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodServiceImpl implements FoodService {
    
    @Autowired
    FoodRepository repository;
    
    @Override
    
    public Iterable<Food> findAll() {
        return repository.findAll();
    }
    
    @Override
    public Optional<Food> findById(Integer id) {
        return repository.findById(id);
    }
    
    @Override
    public Food save(Food t) {
        return repository.save(t);
    }
    
    @Override
    public Iterable<Food> findByFoodIDIn(List<Integer> foodIds) {
        return repository.findByFoodIDIn(foodIds);
    }
 
    
}
