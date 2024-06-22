/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.Ingredient;
import java.time.LocalDate;
import java.util.List;
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
public class FoodEntityResponse {

    private int foodID;

    private String foodName;

    private int foodCalories;

    private String description;

    private String foodPhoto;

    private String foodVideo;

    private String foodNutrition;

    private int foodTimeProcess;

    private LocalDate creationDate;

    private String serving;

    private boolean isActive;

    private List<TagResponse> foodTags;

    private List<Ingredient> ingredients;

    public FoodEntityResponse(Food food, List<TagResponse> foodTags, List<Ingredient> ingredients) {
        this.foodID = food.getFoodID();
        this.foodName = food.getFoodName();
        this.foodCalories = food.getFoodCalories();
        this.description = food.getDescription();
        this.foodPhoto = food.getFoodPhoto();
        this.foodVideo = food.getFoodVideo();
        this.foodNutrition = food.getFoodNutrition();
        this.foodTimeProcess = food.getFoodTimeProcess();
        this.serving = food.getServing();
        this.creationDate = food.getCreationDate();
        this.isActive = food.getIsActive();
        this.foodTags = foodTags;
        this.ingredients = ingredients;
    }
}
