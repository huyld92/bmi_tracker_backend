/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Ingredient;
import com.fu.bmi_tracker.payload.request.CreateIngredientRequest;
import com.fu.bmi_tracker.services.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Ingredient", description = "Ingredient management APIs")
@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    @Autowired
    IngredientService service;

    @Operation(
            summary = "Create new ingredient with form (Admin)",
            description = "Create new ingredient with form: include MultiplePath for ingredient photo"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Ingredient.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewIngredient(@RequestBody CreateIngredientRequest createIngredientRequest) {
        Ingredient ingredient = new Ingredient(createIngredientRequest);

        Ingredient ingredientSave = service.save(ingredient);

        if (ingredientSave == null) {
            return new ResponseEntity<>("Failed to create new ingredient", HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(ingredientSave, HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve all Ingredients")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Ingredient.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no Ingredients", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ADVISOR')")
    public ResponseEntity<?> getAllIngredients() {

        Iterable<Ingredient> ingredients = service.findAll();

        if (!ingredients.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(ingredients, HttpStatus.OK);

    }

    @Operation(summary = "Retrieve a Ingredient by Id", description = "Get a Ingredient object by specifying its id. The response is Ingredient object")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Ingredient.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getByID/{id}")
    public ResponseEntity<?> getIngredientById(@PathVariable("id") int id) {
        Optional<Ingredient> ingredient = service.findById(id);

        if (ingredient.isPresent()) {
            return new ResponseEntity<>(ingredient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find ingredient with id{" + id + "}", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update a Ingredient by Id (ADMIN)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Ingredient.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())})})
    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateIngredient(@RequestBody Ingredient ingredientRequest) {
        Optional<Ingredient> ingredient = service.findById(ingredientRequest.getIngredientID());

        if (ingredient.isPresent()) {
            ingredient.get().update(ingredientRequest);
            return new ResponseEntity<>(service.save(ingredient.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find ingredient with id{" + ingredientRequest.getIngredientID() + "}",
                    HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a Ingredient by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteIngredient(@PathVariable("id") int id) {
        Optional<Ingredient> ingredient = service.findById(id);

        if (ingredient.isPresent()) {
            ingredient.get().setIsActive(Boolean.FALSE);
            return new ResponseEntity<>(service.save(ingredient.get()), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Cannot find ingredient with id{" + id + "}", HttpStatus.NOT_FOUND);
        }
    }
}
