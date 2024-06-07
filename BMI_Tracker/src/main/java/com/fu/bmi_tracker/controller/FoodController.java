/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.Ingredient;
import com.fu.bmi_tracker.payload.request.CreateFoodRequest;
import com.fu.bmi_tracker.payload.request.CreateRecipesRequest;
import com.fu.bmi_tracker.payload.request.UpdateFoodRequest;
import com.fu.bmi_tracker.payload.response.FoodEntityResponse;
import com.fu.bmi_tracker.payload.response.FoodResponseAll;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.services.FoodService;
import com.fu.bmi_tracker.services.RecipeService;

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

    @Operation(
            summary = "Create new food with form",
            description = "Create new food with form")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = FoodEntityResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewFood(@Valid @RequestBody CreateFoodRequest createFoodRequest) {
        // create food from request
        Food food = new Food(createFoodRequest);
        // Create list tag from createFoodRequest
        List<com.fu.bmi_tracker.model.entities.Tag> tags = new ArrayList<>();
        // Add tagID to Tag
        createFoodRequest.getTagIDs().forEach((Integer tagID) -> {
            tags.add(new com.fu.bmi_tracker.model.entities.Tag(tagID));
        });
        // set tags to food
        food.setFoodTags(tags);

        // Add ingredientID to Ingredient
        List<Ingredient> ingredients = new ArrayList<>();
        createFoodRequest.getIngredientIDs().forEach((Integer ingredientID) -> {
            ingredients.add(new Ingredient(ingredientID));
        });
        // set ingredients to food
        food.setIngredients(ingredients);

        // Store food
        Food foodSave = foodService.save(food);

        if (foodSave == null) {
            return new ResponseEntity<>("Failed to create new food", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // create food response
        FoodEntityResponse foodResponse = new FoodEntityResponse(foodSave,
                TagConverter.convertToTagResponseList(foodSave.getFoodTags()),
                foodSave.getIngredients());

        return new ResponseEntity<>(foodResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve all Foods ")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = FoodResponseAll.class), mediaType = "application/json")}),
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
        //chuyển đổi từ food sang FoodResponseAll
        List<FoodResponseAll> foodsResponse = new ArrayList<>();
        for (Food food : foods) {
            FoodResponseAll foodResponse = new FoodResponseAll(food,
                    // chuyển đổi từ Tag sáng Tag Response
                    TagConverter.convertToTagResponseList(food.getFoodTags()));

            foodsResponse.add(foodResponse);
        }

//        foodsResponse.add(new FoodResponse(foodSaved, foodTags, recipes));
        return new ResponseEntity<>(foodsResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve a Food by Id",
            description = "Get a Food object by specifying its id. The response is Food object")
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
            // chuyển đổi thành food response
            FoodEntityResponse foodResponse = new FoodEntityResponse(food.get(),
                    // Chuyển đổi Tag sang TagResponse
                    TagConverter.convertToTagResponseList(food.get().getFoodTags()),
                    food.get().getIngredients());

            return new ResponseEntity<>(foodResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find food with id{" + id + "}", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update a Food by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Food.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())})})
    @PutMapping("/update")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateFood(@RequestBody UpdateFoodRequest foodRequest) {
        // create UpdatefoodReuqest
        // Tìm food 
        Optional<Food> food = foodService.findById(foodRequest.getFoodID());
        // kiểm tra food tồn tại
        if (food.isPresent()) {
            food.get().update(foodRequest);
            // lưu trữ cập nhật xuống databse
            foodService.save(food.get());

            // tạo food reponse 
            FoodEntityResponse foodResponse = new FoodEntityResponse(food.get(),
                    TagConverter.convertToTagResponseList(food.get().getFoodTags()),
                    food.get().getIngredients());

            return new ResponseEntity<>(foodResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find food with id{" + foodRequest.getFoodID() + "}", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Deactive a Food by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/deactive/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deactiveFood(@PathVariable("id") int id) {
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

    @Operation(summary = "Create food recipes")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping("/createRecipes")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createFoodRecipes(@RequestBody CreateRecipesRequest createRecipesRequest) {
        recipeService.createRecipes(createRecipesRequest);
        return new ResponseEntity<>(new MessageResponse("Create food recipes success!"), HttpStatus.OK);

    }

    @Operation(summary = "Create food recipe", description = "")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping("/createRecipe")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createFoodRecipe(@RequestParam Integer foodID, @RequestParam Integer ingredientID) {
        Recipe recipe = recipeService.createRecipe(foodID, ingredientID);

        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @Operation(summary = "Deactive a Food by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/deleteRecipe")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deactiveFood(@RequestParam Integer foodID, @RequestParam Integer ingredientID) {
        recipeService.deleteRecipe(foodID, ingredientID);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
