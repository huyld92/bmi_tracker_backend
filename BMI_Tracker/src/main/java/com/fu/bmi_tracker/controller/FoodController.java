/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.FoodTag;
import com.fu.bmi_tracker.model.entities.Ingredient;
import com.fu.bmi_tracker.model.entities.Recipe;
import com.fu.bmi_tracker.model.entities.Trainer;
import com.fu.bmi_tracker.payload.request.CreateFoodRequest;
import com.fu.bmi_tracker.payload.response.FoodResponse;
import com.fu.bmi_tracker.services.FoodService;
import com.fu.bmi_tracker.services.FoodTagService;
import com.fu.bmi_tracker.services.IngredientService;
import com.fu.bmi_tracker.services.RecipeService;
import com.fu.bmi_tracker.services.TagService;
import com.fu.bmi_tracker.services.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Food", description = "Food management APIs")
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/test/foods")
public class FoodController {

    @Autowired
    FoodService service;

    @Autowired
    TrainerService trainerService;

    @Autowired
    TagService tagService;

    @Autowired
    IngredientService ingredientService;

    @Autowired
    FoodTagService foodTagService;

    @Autowired
    RecipeService recipeService;

    @Operation(
            summary = "Create new food with form",
            description = "Create new food with form: include MultiplePath for food photo",
            tags = {"Food"})
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = FoodResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    public ResponseEntity<?> createNewFood(@RequestBody CreateFoodRequest createFoodRequest) {
        Food food = new Food(createFoodRequest);

        Trainer trainer = trainerService.findByAccountID(createFoodRequest.getAccountID());
        food.setTrainer(trainer);

        Food foodSave = service.save(food);

        if (foodSave == null) {
            return new ResponseEntity<>("Failed to create new food", HttpStatus.INTERNAL_SERVER_ERROR);

        }

        List<com.fu.bmi_tracker.model.entities.Tag> tags = tagService.findByTagIDIn(createFoodRequest.getTagIDs());
        List<FoodTag> foodTags = new ArrayList<>();

        tags.forEach(tag -> {
            foodTags.add(new FoodTag(tag, foodSave));
        });

        if (foodTagService.saveAll(foodTags).isEmpty()) {
            return new ResponseEntity<>("Failed to create new food tag", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //store food tag
        List<Recipe> recipes = new ArrayList<>();
        createFoodRequest.getRecipeRequests().forEach(recipeRequest -> {
            Ingredient ingredient = ingredientService.findById(recipeRequest.getIngredientIDs()).get();
            recipes.add(new Recipe(food, ingredient, recipeRequest.getQuantity()));
        });

        if (recipeService.saveAll(recipes).isEmpty()) {
            return new ResponseEntity<>("Failed to create new recipes", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        FoodResponse foodResponse = new FoodResponse(foodSave, foodTags, recipes);

        return new ResponseEntity<>(foodResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve all Foods", tags = {"Food"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Food.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no Foods", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllFoods() {

        Iterable foods = service.findAll();

        if (!foods.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(foods, HttpStatus.OK);

    }

    @Operation(
            summary = "Retrieve a Food by Id",
            description = "Get a Food object by specifying its id. The response is Food object",
            tags = {"Food"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Food.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getByID/{id}")
    public ResponseEntity<?> getFoodById(@PathVariable("id") int id) {
        Optional<Food> food = service.findById(id);

        if (food.isPresent()) {
            return new ResponseEntity<>(food, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find food with id{" + id + "}", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update a Food by Id", tags = {"Food"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Food.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())})})
    @PutMapping("/update")
    public ResponseEntity<?> updateFood(@RequestBody Food foodRequest) {
        Optional<Food> food = service.findById(foodRequest.getFoodID());

        if (food.isPresent()) {
            food.get().update(foodRequest);
            return new ResponseEntity<>(service.save(food.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find food with id{" + foodRequest.getFoodID() + "}", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a Food by Id", tags = {"Food"})
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteFood(@PathVariable("id") int id) {
        Optional<Food> food = service.findById(id);

        if (food.isPresent()) {
            food.get().setStatus("InActive");
            return new ResponseEntity<>(service.save(food.get()), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Cannot find food with id{" + id + "}", HttpStatus.NOT_FOUND);
        }
    }
}
