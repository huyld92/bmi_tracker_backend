/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Recipe;
import com.fu.bmi_tracker.repository.RecipeRepository;
import com.fu.bmi_tracker.services.RecipeService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    RecipeRepository repository;

    @Override
    public Iterable<Recipe> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Recipe> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Recipe save(Recipe t) {
        return repository.save(t);
    }

    @Override
    public List<Recipe> saveAll(List<Recipe> recipes) {
        return repository.saveAll(recipes);
    }

}
