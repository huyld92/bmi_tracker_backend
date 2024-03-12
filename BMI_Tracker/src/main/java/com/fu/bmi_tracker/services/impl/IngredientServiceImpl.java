/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Ingredient;
import com.fu.bmi_tracker.repository.IngredientRepository;
import com.fu.bmi_tracker.services.IngredientService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    IngredientRepository repository;

    @Override
    public Iterable<Ingredient> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Ingredient> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Ingredient save(Ingredient t) {
        return repository.save(t);
    }

}
