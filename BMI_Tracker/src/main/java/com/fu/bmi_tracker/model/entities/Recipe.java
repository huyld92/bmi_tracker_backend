/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.payload.request.RecipeRequest;
import com.fu.bmi_tracker.payload.request.UpdateFoodRecipeRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Duc Huy
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecipeID", nullable = false)
    private Integer recipeID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FoodID")
    private Food food;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IngredientID")
    private Ingredient ingredient;

    @Column(name = "quantity", nullable = false)
    private Float quantity;

    @Column(name = "Unit", nullable = false)
    private String unit;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    public Recipe(Food food, Ingredient ingredient, RecipeRequest recipeRequest) {
        this.food = food;
        this.ingredient = ingredient;
        this.unit = recipeRequest.getUnit();
        this.quantity = recipeRequest.getQuantity();
        this.isActive = true;
    }

    public Recipe(Food food, Ingredient ingredient, Float quantity, String unit, Boolean isActive) {
        this.food = food;
        this.ingredient = ingredient;
        this.unit = unit;
        this.quantity = quantity;
        this.isActive = isActive;
    }
}
