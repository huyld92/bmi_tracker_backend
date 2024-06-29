/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.payload.request.RecipeRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@IdClass(Recipe.class)
@Table(name = "Recipe")
public class Recipe {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FoodID")
    private Food food;

    @Id
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
        this.isActive = recipeRequest.getIsActive();
    }

}
