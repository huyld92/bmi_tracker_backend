/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.FoodDetails;
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
public class FoodDetailsResponse {

    private Integer recipeID;
    private Integer ingredientID;
    private String ingredientName;
    private String ingredientPhoto;
    private Float quantity;
    private String unit;

    public FoodDetailsResponse(FoodDetails foodDetails) {
        this.recipeID = foodDetails.getFoodDetailsID();
        this.ingredientID = foodDetails.getIngredient().getIngredientID();
        this.ingredientName = foodDetails.getIngredient().getIngredientName();
        this.ingredientPhoto = foodDetails.getIngredient().getIngredientPhoto();
        this.quantity = foodDetails.getQuantity();
        this.unit = foodDetails.getUnit();
    }

}
