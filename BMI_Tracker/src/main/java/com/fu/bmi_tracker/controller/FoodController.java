/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.FoodTag;
import com.fu.bmi_tracker.model.entities.Ingredient;
import com.fu.bmi_tracker.model.entities.Recipe;
import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.payload.request.CreateFoodRequest;
import com.fu.bmi_tracker.payload.response.FoodResponse;
import com.fu.bmi_tracker.services.FoodService;
import com.fu.bmi_tracker.services.FoodTagService;
import com.fu.bmi_tracker.services.IngredientService;
import com.fu.bmi_tracker.services.RecipeService;
import com.fu.bmi_tracker.services.TagService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fu.bmi_tracker.services.AdvisorService;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Food", description = "Food management APIs")
@RestController
@RequestMapping("/api/foods")
public class FoodController {

    @Autowired
    FoodService service;

    @Autowired
    AdvisorService advisorService;

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
            description = "Create new food with form")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = FoodResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> createNewFood(@RequestBody CreateFoodRequest createFoodRequest) {
        // create food from request
        Food food = new Food(createFoodRequest);
        //Check advisor
        Advisor advisor = advisorService.findByAccountID(createFoodRequest.getAccountID());
        food.setAdvisor(advisor);

        // Store food
        Food foodSave = service.save(food);

        if (foodSave == null) {
            return new ResponseEntity<>("Failed to create new food", HttpStatus.INTERNAL_SERVER_ERROR);

        }

        // Find list tag
        List<com.fu.bmi_tracker.model.entities.Tag> tags = tagService.findByTagIDIn(createFoodRequest.getTagIDs());
        List<FoodTag> foodTags = new ArrayList<>();

        // Create food tag from list tag
        tags.forEach(tag -> {
            foodTags.add(new FoodTag(tag, foodSave));
        });

        // store food tag
        if (foodTagService.saveAll(foodTags).isEmpty()) {
            return new ResponseEntity<>("Failed to create new food tag", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<Recipe> recipes = new ArrayList<>();

        // Create reccipes
        createFoodRequest.getRecipeRequests().forEach(recipeRequest -> {
            Ingredient ingredient = ingredientService.findById(recipeRequest.getIngredientID()).get();
            recipes.add(new Recipe(food, ingredient, recipeRequest.getQuantity()));
        });

        // store recipe
        if (recipeService.saveAll(recipes).isEmpty()) {
            return new ResponseEntity<>("Failed to create new recipes", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // Create food reponse
        FoodResponse foodResponse = new FoodResponse(foodSave, tags, recipes);

        return new ResponseEntity<>(foodResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve all Foods (ADMIN)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Food.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no Foods", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllFoods() {
        Iterable<Food> foods = service.findAll();

        if (!foods.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve a Food by Id",
            description = "Get a Food object by specifying its id. The response is Food object")
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

    @Operation(summary = "Update a Food by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Food.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())})})
    @PutMapping("/update")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> updateFood(@RequestBody Food foodRequest) {
        Optional<Food> food = service.findById(foodRequest.getFoodID());

        if (food.isPresent()) {
            food.get().update(foodRequest);
            return new ResponseEntity<>(service.save(food.get()), HttpStatus.OK);
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
    public ResponseEntity<?> deactiveFood(@PathVariable("id") int id) {
        Optional<Food> food = service.findById(id);

        if (food.isPresent()) {
            food.get().setIsActive(Boolean.FALSE);
            return new ResponseEntity<>(service.save(food.get()), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Cannot find food with id{" + id + "}", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Retrieve all Foods by advisor id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Food.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no Foods", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getAllByAdvisorID/")
    public ResponseEntity<?> getAllFoodsOfAdvisor(@RequestParam int advisorID) {

        Iterable<Food> foods = service.findByAdvisorID(advisorID);

        if (!foods.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(foods, HttpStatus.OK);

    }
}
