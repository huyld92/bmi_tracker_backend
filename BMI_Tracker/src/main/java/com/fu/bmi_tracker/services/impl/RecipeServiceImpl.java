/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.Ingredient;
import com.fu.bmi_tracker.model.entities.Recipe;
import com.fu.bmi_tracker.payload.request.CreateRecipesRequest;
import com.fu.bmi_tracker.repository.FoodRepository;
import com.fu.bmi_tracker.repository.IngredientRepository;
import com.fu.bmi_tracker.repository.RecipeRepository;
import com.fu.bmi_tracker.services.RecipeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    public List<Recipe> saveAll(List<Recipe> recipes) {
        return repository.saveAll(recipes);
    }

    @Override
    public void createRecipes(CreateRecipesRequest createRecipesRequest) {
        Food food = foodRepository.findById(createRecipesRequest.getFoodID())
                .orElseThrow(() -> new EntityNotFoundException("Food not found"));

        List<Ingredient> ingredients = createRecipesRequest.getIngredientIDs().stream()
                .map(id -> ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient {" + id + "}  not found")))
                .collect(Collectors.toList());

        for (Ingredient ingredient : ingredients) {
            Recipe recipe = new Recipe();
            recipe.setFood(food);
            recipe.setIngredient(ingredient);
            repository.save(recipe);
        }

    }

    @Override
    public Recipe createRecipe(Integer foodID, Integer ingredientID) {
        Food food = foodRepository.findById(foodID)
                .orElseThrow(() -> new EntityNotFoundException("Food not found"));

        Ingredient ingredient = ingredientRepository.findById(ingredientID)
                .orElseThrow(() -> new EntityNotFoundException("Ingredient not found"));

        Recipe recipe = new Recipe(food, ingredient);
        recipe.setFood(food);
        recipe.setIngredient(ingredient);
        return repository.save(recipe);

    }

    @Override
    @Transactional
    public void deleteRecipe(Integer foodID, Integer ingredientID) {
        repository.deleteByFood_FoodIDAndIngredient_IngredientID(foodID, ingredientID);
    }

}
