/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.util;

import com.fu.bmi_tracker.model.entities.Recipe;
import com.fu.bmi_tracker.payload.response.RecipeResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Duc Huy
 */
public class RecipeConverter {

    public static RecipeResponse convertToRecipeResponse(Recipe recipe) {
        RecipeResponse recipeResponse = new RecipeResponse(recipe);

        return recipeResponse;
    }

    public static List<RecipeResponse> convertToRecipeResponseList(List<Recipe> recipes) {
        return recipes.stream()
                .map(RecipeConverter::convertToRecipeResponse)
                .collect(Collectors.toList());
    }
}
