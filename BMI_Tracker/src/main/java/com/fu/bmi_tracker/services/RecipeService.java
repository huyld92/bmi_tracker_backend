/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Recipe;
import com.fu.bmi_tracker.payload.request.CreateRecipeRequest;

/**
 *
 * @author Duc Huy
 */
public interface RecipeService extends GeneralService<Recipe> {

    public void deleteRecipe(Integer foodID, Integer ingredientID);

    public Recipe createRecipe(CreateRecipeRequest recipeRequest);

}
