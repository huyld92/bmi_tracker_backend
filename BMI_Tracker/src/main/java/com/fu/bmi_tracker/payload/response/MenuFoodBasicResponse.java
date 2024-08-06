/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.enums.EMealType;
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
public class MenuFoodBasicResponse {

    private Integer menuFoodID;

    private Integer foodID;

    private String foodName;

    private Integer foodCalories;

    private String description;

    private String foodPhoto;

    private String foodVideo;

    private Integer foodTimeProcess;

    private Integer serving;

    private Float carbs;

    private Float protein;

    private Float fat;

    private EMealType mealType;

    public MenuFoodBasicResponse(Integer menuFoodID, Food food, EMealType mealType) {
        this.menuFoodID = menuFoodID;
        this.foodID = food.getFoodID();
        this.foodName = food.getFoodName();
        this.foodCalories = food.getFoodCalories();
        this.description = food.getDescription();
        this.foodPhoto = food.getFoodPhoto();
        this.foodVideo = food.getFoodVideo();
        this.carbs = food.getCarbs();
        this.protein = food.getProtein();
        this.fat = food.getFat();
        this.foodTimeProcess = food.getFoodTimeProcess();
        this.serving = food.getServing();
        this.mealType = mealType;
    }

}
