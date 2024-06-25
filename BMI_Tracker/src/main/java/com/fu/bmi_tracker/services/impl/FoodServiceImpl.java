/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.Ingredient;
import com.fu.bmi_tracker.model.entities.Recipe;
import com.fu.bmi_tracker.model.entities.Tag;
import com.fu.bmi_tracker.payload.request.CreateFoodRequest;
import com.fu.bmi_tracker.payload.request.RecipeRequest;
import com.fu.bmi_tracker.repository.FoodRepository;
import com.fu.bmi_tracker.repository.IngredientRepository;
import com.fu.bmi_tracker.services.FoodService;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Override
    public Iterable<Food> findAll() {
        return foodRepository.findAll();
    }

    @Override
    public Optional<Food> findById(Integer id) {
        return foodRepository.findById(id);
    }

    @Override
    public Food save(Food t) {
        return foodRepository.save(t);
    }

    @Override
    public Iterable<Food> findByFoodIDIn(List<Integer> foodIds) {
        return foodRepository.findByFoodIDIn(foodIds);
    }

    @Override
    public Page<Food> getFoodsByTagName(String dietPreferenceName, Pageable pageable) {
        return foodRepository.findFoodWithTagName(dietPreferenceName, pageable);
    }

    @Override
    public Food createNewFood(CreateFoodRequest createFoodRequest) {
        // Tạo mới food từ createFoodRequest
        Food food = new Food(createFoodRequest);

        // tạo list tag từ createFoodRequest
        List< Tag> tags = new ArrayList<>();

        // Add tagID to Tag
        createFoodRequest.getTagIDs().forEach((Integer tagID) -> {
            tags.add(new Tag(tagID));
        });

        // set tags to food
        food.setFoodTags(tags);

        // Chuyển đổi từ List RecipeRequest thành List Recipe
        List<Recipe> recipes = new ArrayList<>();
        createFoodRequest.getRecipeRequests().forEach((RecipeRequest recipeRequest) -> {
            // gọi ingredient repository tìm ingredient
            Ingredient ingredient = ingredientRepository.findById(recipeRequest.getIngredientID())
                    .orElseThrow(() -> new EntityNotFoundException("Cannot find ingredient with ID {" + recipeRequest.getIngredientID() + "}"));

            recipes.add(new Recipe(food, ingredient, recipeRequest));
        });

        //Set recipes vào food
        food.setRecipes(recipes);

        // Gọi foodRepository lưu trữ food
        return foodRepository.save(food);
    }

}
