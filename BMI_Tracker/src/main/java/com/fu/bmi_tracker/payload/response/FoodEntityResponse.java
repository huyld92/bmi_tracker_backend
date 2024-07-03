/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Food;
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

    private Integer foodID;

    private String foodName;

    private Integer foodCalories;

    private String description;

    private String foodPhoto;

    private String foodVideo;

    private String foodNutrition;

    private Integer foodTimeProcess;

    private LocalDate creationDate;

    private String serving;

    private Boolean isActive;

    private List<TagBasicResponse> foodTags;

    private List<RecipeResponse> recipes;

    public FoodEntityResponse(Food food, List<TagBasicResponse> foodTags, List<RecipeResponse> recipes) {
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
        this.recipes = recipes;
    }
}
