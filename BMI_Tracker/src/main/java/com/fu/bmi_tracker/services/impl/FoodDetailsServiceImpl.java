/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.Ingredient;
import com.fu.bmi_tracker.model.entities.FoodDetails;
import com.fu.bmi_tracker.payload.request.CreateRecipeRequest;
import com.fu.bmi_tracker.repository.FoodRepository;
import com.fu.bmi_tracker.repository.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.repository.FoodDetailsRepository;
import com.fu.bmi_tracker.services.FoodDetailsService;

@Service
public class FoodDetailsServiceImpl implements FoodDetailsService {

    @Autowired
    FoodDetailsRepository repository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Override
    public Iterable<FoodDetails> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<FoodDetails> findById(Integer id) {
        // return repository.findById(id);
        return null;
    }

    @Override
    public FoodDetails save(FoodDetails t) {
        return repository.save(t);
    }

    @Override
    public FoodDetails createRecipe(CreateRecipeRequest recipeRequest) {
        // gọi foodRepository tìm food bằng foodID
        Food food = foodRepository.findById(recipeRequest.getFoodID())
                .orElseThrow(() -> new EntityNotFoundException("Food not found"));

        // gọi ingredientRepository tìm ingredient bằng ingredientID
        Ingredient ingredient = ingredientRepository.findById(recipeRequest.getIngredientID())
                .orElseThrow(() -> new EntityNotFoundException("Ingredient not found"));

        // tạo recipe full contructor
        FoodDetails recipe = new FoodDetails(food,
                ingredient,
                recipeRequest.getQuantity(),
                recipeRequest.getUnit());

        return repository.save(recipe);

    }

    @Override
    @Transactional
    public void deleteFoodDetails(Integer foodDetailsID) {
        repository.deleteById(foodDetailsID);
    }

}
