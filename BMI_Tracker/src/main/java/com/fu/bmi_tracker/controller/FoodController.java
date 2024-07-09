/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.Recipe;
import com.fu.bmi_tracker.payload.request.CreateFoodRequest;
import com.fu.bmi_tracker.payload.request.CreateRecipeRequest;
import com.fu.bmi_tracker.payload.request.UpdateFoodRequest;
import com.fu.bmi_tracker.payload.response.FoodEntityResponse;
import com.fu.bmi_tracker.payload.response.FoodResponse;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.payload.response.RecipeResponse;
import com.fu.bmi_tracker.services.FoodService;
import com.fu.bmi_tracker.services.RecipeService;
import com.fu.bmi_tracker.util.RecipeConverter;

import com.fu.bmi_tracker.util.TagConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Food", description = "Food management APIs")
@RestController
@RequestMapping("/api/foods")
public class FoodController {

    @Autowired
    FoodService foodService;

    @Autowired
    RecipeService recipeService;

    @Operation(summary = "Create new food with form", description = "Create new food with form")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = FoodEntityResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewFood(@Valid @RequestBody CreateFoodRequest createFoodRequest) {
        // gọi foodService Tạo mới food
        Food food = foodService.createNewFood(createFoodRequest);

        // Tạo food entity response
        FoodEntityResponse foodResponse = new FoodEntityResponse(food,
                TagConverter.convertToTagBasicResponseList(food.getFoodTags()),
                RecipeConverter.convertToRecipeResponseList(food.getRecipes()));

        return new ResponseEntity<>(foodResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve all Foods ")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = FoodEntityResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no Foods", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllFoods() {
        // Lấy danh sách food từ service
        Iterable<Food> foods = foodService.findAll();

        // kiểm tra empty
        if (!foods.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // chuyển đổi từ food sang FoodEntityResponse
        List<FoodEntityResponse> foodsResponse = new ArrayList<>();
        for (Food food : foods) {
            FoodEntityResponse foodResponse = new FoodEntityResponse(
                    food,
                    TagConverter.convertToTagBasicResponseList(food.getFoodTags()),
                    RecipeConverter.convertToRecipeResponseList(food.getRecipes()));

            foodsResponse.add(foodResponse);
        }

        return new ResponseEntity<>(foodsResponse, HttpStatus.OK);
    }

    @Operation(summary = "Retrieve a Food by Id", description = "Get a Food object by specifying its id. The response is Food object")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = FoodEntityResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getByID/{id}")
    public ResponseEntity<?> getFoodById(@PathVariable("id") int id) {
        // tìm food bằng food id
        Optional<Food> food = foodService.findById(id);
        // kiểm tra tồn tại
        if (food.isPresent()) {
            // Tạo food entity response
            FoodEntityResponse foodResponse = new FoodEntityResponse(food.get(),
                    TagConverter.convertToTagBasicResponseList(food.get().getFoodTags()),
                    RecipeConverter.convertToRecipeResponseList(food.get().getRecipes()));

            return new ResponseEntity<>(foodResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse("Cannot find food with id{" + id + "}"),
                    HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update a Food by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = FoodEntityResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())})})
    @PutMapping("/update")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateFood(@RequestBody UpdateFoodRequest foodRequest) {
        Food food = foodService.updateFood(foodRequest);
        // kiểm tra kết quả
        if (food != null) {
            // Tạo food entity response
            FoodEntityResponse foodResponse = new FoodEntityResponse(food,
                    TagConverter.convertToTagBasicResponseList(food.getFoodTags()),
                    RecipeConverter.convertToRecipeResponseList(food.getRecipes()));

            return new ResponseEntity<>(foodResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse("Error update food!"), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Deactivate a Food by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema(implementation = RecipeResponse.class))}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/deactivate/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deactivateFood(@PathVariable("id") int id) {
        // Tim food by id
        Optional<Food> food = foodService.findById(id);

        if (food.isPresent()) {
            // set isActive false
            food.get().setIsActive(Boolean.FALSE);
            return new ResponseEntity<>(foodService.save(food.get()), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Deactivate a recipe by recipeID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema(implementation = RecipeResponse.class))}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/recipe/deactivate")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deactivateRecipe(@RequestParam Integer recipeID) {
        // Gọi recipeService deactivate recipe
        recipeService.deactivateRecipe(recipeID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @Operation(summary = "Create food recipe", description = "")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping("/createRecipe")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createFoodRecipe(@RequestBody CreateRecipeRequest recipeRequest) {
        // gọi recipeService tạo mới recipe
        Recipe recipe = recipeService.createRecipe(recipeRequest);

        // chuyển đổi từ recipe sáng RecipesResponse
        RecipeResponse recipeResponse = new RecipeResponse(recipe);

        return new ResponseEntity<>(recipeResponse, HttpStatus.OK);
    }

    @Operation(summary = "Delete a recipe of Food by recipeID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/deleteRecipe")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRecipe(@RequestParam Integer recipeID) {
        recipeService.deleteRecipe(recipeID);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

//    @Operation(summary = "Retrieve list food with dietPreferene", description = "Get a Food list with")
//    @ApiResponses({
//        @ApiResponse(responseCode = "200", content = {
//            @Content(schema = @Schema(implementation = FoodPageResponse.class), mediaType = "application/json")}),
//        @ApiResponse(responseCode = "404", content = {
//            @Content(schema = @Schema())}),
//        @ApiResponse(responseCode = "500", content = {
//            @Content(schema = @Schema())})})
//    @GetMapping("/getByDietPreference")
//    public ResponseEntity<?> getFoodWithDietPreference(@RequestParam EDietPreference dietPreferenceName,
//            Pageable pageable) {
//        Page<Food> foods = foodService.getFoodsByTagName(dietPreferenceName.toString(), pageable);
//        if (foods.isEmpty()) {
//
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        FoodPageResponse foodPageResponses = new FoodPageResponse();
//        List<FoodResponse> foodResponses = new ArrayList<>();
//        for (Food food : foods.getContent()) {
//            FoodResponse response = new FoodResponse(food);
//            foodResponses.add(response);
//        }
//        foodPageResponses.setFoods(foodResponses);
//        foodPageResponses.setPageNumber(foods.getNumber());
//        foodPageResponses.setPageSize(foods.getSize());
//        foodPageResponses.setTotalElements(foods.getTotalElements());
//        foodPageResponses.setTotalPages(foods.getTotalPages());
//        foodPageResponses.setLast(foods.isLast());
//        foodPageResponses.setFirst(foods.isFirst());
//        foodPageResponses.setNumberOfElements(foods.getNumberOfElements());
//
//        return new ResponseEntity<>(foodPageResponses, HttpStatus.OK);
//
//    }

    @Operation(summary = "Search food by name", description = "Search food with like name")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = FoodResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/search-by-name")
    public ResponseEntity<?> getFoodWithDietPreference(@RequestParam String foodName) {
        // gọi service tìm food bằng foodName
        Iterable<Food> foods = foodService.searchLikeFoodName(foodName.trim());

        // kiểm tra empty
        if (!foods.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // chuyển đổi từ food sang FoodResponse
        List<FoodResponse> foodsResponse = new ArrayList<>();
        for (Food food : foods) {
            FoodResponse foodResponse = new FoodResponse(food);

            foodsResponse.add(foodResponse);
        }
        return new ResponseEntity<>(foodsResponse, HttpStatus.OK);

    }

    @Operation(summary = "Retrieve all recipes by foodID ")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = RecipeResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no Foods", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("recipe/getAll")
    public ResponseEntity<?> getAllRecipesByFoodID(@RequestParam Integer foodID) {
        // Lấy danh sách food từ service
        Iterable<Recipe> recipes = foodService.findAllRecipesByFoodID(foodID);

        // kiểm tra empty
        if (!recipes.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // chuyển đổi từ recipe sang RecipeResponse
        List<RecipeResponse> recipesResponse = new ArrayList<>();
        for (Recipe recipe : recipes) {
            RecipeResponse recipeResponse = new RecipeResponse(recipe);

            recipesResponse.add(recipeResponse);
        }

        return new ResponseEntity<>(recipesResponse, HttpStatus.OK);
    }
}
