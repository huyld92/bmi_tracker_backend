/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Ingredient;
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
public class IngredientResponse {

    private Integer ingredientID;
    private String ingredientName;
    private String ingredientPhoto;
    private String nutritionalInformation;
    private Float quantity;
    private String unit;
    private Integer ingredientCalories;
    private String tagName;
    private Boolean isActive;

    public IngredientResponse(Ingredient ingredient) {
        this.ingredientID = ingredient.getIngredientID();
        this.ingredientName = ingredient.getIngredientName();
        this.ingredientPhoto = ingredient.getIngredientPhoto();
        this.nutritionalInformation = ingredient.getNutritionalInformation();
        this.quantity = ingredient.getQuantity();
        this.unit = ingredient.getUnit();
        this.ingredientCalories = ingredient.getIngredientCalories();
        this.tagName = ingredient.getTag().getTagName();
        this.isActive = ingredient.getIsActive();
    }

}
