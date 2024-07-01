/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Duc Huy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponse {

    private Integer recipeID;
    private Integer ingredientID;
    private String ingredientName;
    private String ingredientPhoto;
    private Float quantity;
    private String unit;
    private Boolean isActive;

    public RecipeResponse(Recipe recipe) {
        this.recipeID = recipe.getRecipeID();
        this.ingredientID = recipe.getIngredient().getIngredientID();
        this.ingredientName = recipe.getIngredient().getIngredientName();
        this.ingredientPhoto = recipe.getIngredient().getIngredientPhoto();
        this.quantity = recipe.getQuantity();
        this.unit = recipe.getUnit();
        this.isActive = recipe.getIsActive();
    }

}
