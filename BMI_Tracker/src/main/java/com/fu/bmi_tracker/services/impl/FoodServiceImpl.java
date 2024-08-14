/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.exceptions.DuplicateRecordException;
import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.Ingredient;
import com.fu.bmi_tracker.model.entities.FoodDetails;
import com.fu.bmi_tracker.model.entities.Tag;
import com.fu.bmi_tracker.payload.request.CreateFoodRequest;
import com.fu.bmi_tracker.payload.request.RecipeRequest;
import com.fu.bmi_tracker.payload.request.UpdateFoodRequest;
import com.fu.bmi_tracker.repository.FoodRepository;
import com.fu.bmi_tracker.repository.IngredientRepository;
import com.fu.bmi_tracker.repository.TagRepository;
import com.fu.bmi_tracker.services.FoodService;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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

    @Autowired
    TagRepository tagRepository;

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
        // lấy danh sách tất cả food
        Iterable<Food> foods = foodRepository.findByIsActiveTrue();

        // kiểm tra tồn tại food name và recipe
        foods.forEach(food -> {
            // kiểm tra foodName
            if (food.getFoodName().equals(createFoodRequest.getFoodName())) {
                throw new DuplicateRecordException("Food name already exists");
            } else if (food.getFoodDetails().size() == createFoodRequest.getRecipeRequests().size()) {
                // Nếu kích thước recipe giống nhau thì kiểm tra thành phần

                // sử dụng Stream tách danh sách ingredientID của foods
                Set<Integer> ingredientIDs = food.getFoodDetails().stream()
                        .map(foodDetails -> foodDetails.getIngredient().getIngredientID())
                        .collect(Collectors.toSet());

                //  sử dụng Stream tách danh sách ingredientID từ  RecipeRequests 
                Set<Integer> ingredientIDsRequest = createFoodRequest.getRecipeRequests().stream().map(
                        recipeRequest -> recipeRequest.getIngredientID())
                        .collect(Collectors.toSet());

                // kiểm tra ingredientIDs có trùng với Set<IngredientID>
                boolean isDuplicate = ingredientIDsRequest.stream().allMatch(ingredientIDs::contains);
                if (isDuplicate) {
                    throw new DuplicateRecordException("Recipe already exists");
                }
            }
        });

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

        // Chuyển đổi từ List RecipeRequest thành List FoodDetails
        List<FoodDetails> foodDetailses = new ArrayList<>();
        createFoodRequest.getRecipeRequests().forEach((RecipeRequest recipeRequest) -> {
            // gọi ingredient repository tìm ingredient
            Ingredient ingredient = ingredientRepository.findByIngredientIDAndIsActiveTrue(recipeRequest.getIngredientID())
                    .orElseThrow(() -> new EntityNotFoundException("Cannot find ingredient with ID {" + recipeRequest.getIngredientID() + "}"));

            foodDetailses.add(new FoodDetails(food, ingredient, recipeRequest));
        });

        //Set recipes vào food
        food.setFoodDetails(foodDetailses);

        // Gọi foodRepository lưu trữ food
        return foodRepository.save(food);
    }

    @Override
    public Food updateFood(UpdateFoodRequest foodRequest) {
        // tìm food bằng foodID
        Food food = foodRepository.findByFoodIDAndIsActiveTrue(foodRequest.getFoodID())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find food with id{" + foodRequest.getFoodID() + "}!"));

        //validate foodName
        if (!food.getFoodName().equals(foodRequest.getFoodName())) {
            // lấy danh sách tất cả food
            Iterable<Food> foods = foodRepository.findByIsActiveTrue();
            
            // kiểm tra tồn tại food name và recipe
            foods.forEach(f -> {
                if (food.getFoodID() != f.getFoodID()) {
                    // kiểm tra foodName
                    if (f.getFoodName().equals(food.getFoodName())) {
                        throw new DuplicateRecordException("Food name already exists");
                    }
                }
            });
        }

        // tìm tag từ list Tag ids
        List<Tag> tags = tagRepository.findByTagIDIn(foodRequest.getTagIDs());

        // cập nhât lại recipe
//        List<FoodDetails> recipes = new ArrayList<>();
//
//        foodRequest.getRecipeRequests().forEach(recipeRequest -> {
//            FoodDetails recipe = recipeRepository.findById(recipeRequest.getRecipeID())
//                    .orElseThrow(() -> new EntityNotFoundException("Cannot find recipe with id{" + recipeRequest.getRecipeID() + "}!"));
//
//            Ingredient ingredient = ingredientRepository.findByIngredientIDAndIsActiveTrue(recipeRequest.getIngredientID())
//                    .orElseThrow(() -> new EntityNotFoundException("Cannot find ingredient with id{" + recipeRequest.getIngredientID() + "}!"));
//            recipe.setFood(food);
//            recipe.setIngredient(ingredient);
//            recipe.setUnit(recipeRequest.getUnit());
//            recipe.setQuantity(recipeRequest.getQuantity());
//            // tạo recipe mới
//            recipes.add(recipe);
//        });
        // Cập nhật Food 
        food.update(foodRequest, tags);

        // Lưu Food đã được cập nhật vào cơ sở dữ liệu
        return foodRepository.save(food);
    }

    @Override
    public Iterable<Food> searchLikeFoodName(String foodName) {
        return foodRepository.findByFoodNameContains(foodName);
    }

    @Override
    public Optional<Food> findByFoodIDAndIsActive(Integer foodID) {
        return foodRepository.findByFoodIDAndIsActiveTrue(foodID);
    }

    @Override
    public Iterable<FoodDetails> findAllRecipesByFoodID(Integer foodID) {
        // tìm food bằng foodID
        Food food = foodRepository.findByFoodIDAndIsActiveTrue(foodID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find food with id{" + foodID + "}!"));

        return food.getFoodDetails();
    }

    @Override
    public Iterable<Food> getAllFoodIsActiveTrue() {
        return foodRepository.findByIsActiveTrue();
    }

    @Override
    public Iterable<Food> getFoodsWithFilterTags(List<Integer> tagIDs) {
        return foodRepository.findAllByTagIDs(tagIDs, tagIDs.size());
    }

}
