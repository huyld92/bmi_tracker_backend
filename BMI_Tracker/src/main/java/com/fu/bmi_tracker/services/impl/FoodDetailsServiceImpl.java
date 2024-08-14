/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.exceptions.DuplicateRecordException;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FoodDetailsServiceImpl implements FoodDetailsService {

    @Autowired
    FoodDetailsRepository foodDetailsRepository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Override
    public Iterable<FoodDetails> findAll() {
        return foodDetailsRepository.findAll();
    }

    @Override
    public Optional<FoodDetails> findById(Integer id) {
        // return foodDetailsRepository.findById(id);
        return null;
    }

    @Override
    public FoodDetails save(FoodDetails t) {
        return foodDetailsRepository.save(t);
    }

    @Override
    public FoodDetails createRecipe(CreateRecipeRequest recipeRequest) {
        // gọi foodRepository tìm food bằng foodID
        Food food = foodRepository.findById(recipeRequest.getFoodID())
                .orElseThrow(() -> new EntityNotFoundException("Food not found"));

        // gọi ingredientRepository tìm ingredient bằng ingredientID
        Ingredient ingredient = ingredientRepository.findById(recipeRequest.getIngredientID())
                .orElseThrow(() -> new EntityNotFoundException("Ingredient not found"));
        //thêm ingredient vào recipe
        FoodDetails recipe = new FoodDetails(food,
                ingredient,
                recipeRequest.getQuantity(),
                recipeRequest.getUnit());

        food.getFoodDetails().add(recipe);

        // validate recipe của food 
        Iterable<Food> foods = foodRepository.findByIsActiveTrue();
        // kiểm tra tồn tại food name và recipe
        foods.forEach(f -> {
            if (f.getFoodID() != food.getFoodID()) {
                // Nếu kích thước recipe giống nhau thì kiểm tra thành phần
                if (f.getFoodDetails().size() == food.getFoodDetails().size()) {
                    // sử dụng Stream tách danh sách ingredientID của foods (Tất cả food)
                    Set<Integer> ingredientIDs = f.getFoodDetails().stream()
                            .map(foodDetails -> foodDetails.getIngredient().getIngredientID())
                            .collect(Collectors.toSet());

                    //  sử dụng Stream tách danh sách ingredientID từ  RecipeRequests 
                    Set<Integer> ingredientIDsRequest = food.getFoodDetails().stream()
                            .map(foodDetails -> foodDetails.getIngredient().getIngredientID())
                            .collect(Collectors.toSet());

                    // kiểm tra ingredientIDs có trùng với Set<IngredientID>
                    boolean isDuplicate = ingredientIDsRequest.stream().allMatch(ingredientIDs::contains);
                    if (isDuplicate) {
                        throw new DuplicateRecordException("Recipe already exists");
                    }
                }
            }
        });

        return foodDetailsRepository.save(recipe);
    }

    @Override
    @Transactional
    public void deleteFoodDetails(Integer foodDetailsID) {
        // gọi foodRepository tìm food bằng foodID
        FoodDetails fDetails = foodDetailsRepository.findById(foodDetailsID)
                .orElseThrow(() -> new EntityNotFoundException("FoodDetails not found"));

        Food food = foodRepository.findById(fDetails.getFood().getFoodID())
                .orElseThrow(() -> new EntityNotFoundException("Food not found"));
        food.getFoodDetails().remove(fDetails);

        // validate recipe của food 
        Iterable<Food> foods = foodRepository.findByIsActiveTrue();
        // kiểm tra tồn tại food name và recipe
        foods.forEach(f -> {
            if (f.getFoodID() != food.getFoodID()) {
                // Nếu kích thước recipe giống nhau thì kiểm tra thành phần
                if (f.getFoodDetails().size() == food.getFoodDetails().size()) {
                    // sử dụng Stream tách danh sách ingredientID của foods (Tất cả food)
                    Set<Integer> ingredientIDs = f.getFoodDetails().stream()
                            .map(foodDetails -> foodDetails.getIngredient().getIngredientID())
                            .collect(Collectors.toSet());

                    //  sử dụng Stream tách danh sách ingredientID từ  RecipeRequests 
                    Set<Integer> ingredientIDsRequest = food.getFoodDetails().stream()
                            .map(foodDetails -> foodDetails.getIngredient().getIngredientID())
                            .collect(Collectors.toSet());

                    // kiểm tra ingredientIDs có trùng với Set<IngredientID>
                    boolean isDuplicate = ingredientIDsRequest.stream().allMatch(ingredientIDs::contains);
                    if (isDuplicate) {
                        throw new DuplicateRecordException("Recipe already exists! Cannot delete Ingredient");
                    }
                }
            }
        });
        foodDetailsRepository.deleteById(foodDetailsID);
    }

}
