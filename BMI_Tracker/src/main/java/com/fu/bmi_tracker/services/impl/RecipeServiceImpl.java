/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.Ingredient;
import com.fu.bmi_tracker.model.entities.Recipe;
import com.fu.bmi_tracker.payload.request.CreateRecipeRequest;
import com.fu.bmi_tracker.repository.FoodRepository;
import com.fu.bmi_tracker.repository.IngredientRepository;
import com.fu.bmi_tracker.repository.RecipeRepository;
import com.fu.bmi_tracker.services.RecipeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    RecipeRepository repository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Override
    public Iterable<Recipe> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Recipe> findById(Integer id) {
        // return repository.findById(id);
        return null;
    }

    @Override
    public Recipe save(Recipe t) {
        return repository.save(t);
    }

    @Override
    public Recipe createRecipe(CreateRecipeRequest recipeRequest) {
        // gọi foodRepository tìm food bằng foodID
        Food food = foodRepository.findById(recipeRequest.getFoodID())
                .orElseThrow(() -> new EntityNotFoundException("Food not found"));

        // gọi ingredientRepository tìm ingredient bằng ingredientID
        Ingredient ingredient = ingredientRepository.findById(recipeRequest.getIngredientID())
                .orElseThrow(() -> new EntityNotFoundException("Ingredient not found"));

        // tạo recipe full contructor
        Recipe recipe = new Recipe(food,
                ingredient,
                recipeRequest.getQuantity(),
                recipeRequest.getUnit(),
                Boolean.TRUE);

        return repository.save(recipe);

    }

    @Override
    @Transactional
    public void deleteRecipe(Integer foodID, Integer ingredientID) {
        repository.deleteByFood_FoodIDAndIngredient_IngredientID(foodID, ingredientID);
    }

    @Override
    public void deactiveRecipe(Integer foodID, Integer ingredientID) {
        // tìm recipe bằng foodID và ingredientID
        Recipe recipe = repository.findByFood_FoodIDAndIngredient_IngredientID(foodID, ingredientID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find member!"));
        // set IsActive  = false để deactivate
        recipe.setIsActive(false);
        // lưu lại recipe đã cập nhật
        save(recipe);
    }

}
