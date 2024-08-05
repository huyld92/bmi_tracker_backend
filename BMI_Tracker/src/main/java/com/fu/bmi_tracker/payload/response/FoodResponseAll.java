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
public class FoodResponseAll {

    private Integer foodID;

    private String foodName;

    private Integer foodCalories;

    private String description;

    private String foodPhoto;

    private String foodVideo;

    private Integer foodTimeProcess;

    private Float carbs;

    private Float protein;

    private Float fat;

    private Integer serving;

    private LocalDate creationDate;

    private Boolean isActive;

    private List<TagBasicResponse> foodTags;

    public FoodResponseAll(Food food, List<TagBasicResponse> foodTags) {
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
        this.creationDate = food.getCreationDate();
        this.serving = food.getServing();
        this.isActive = food.getIsActive();
        this.foodTags = foodTags;
    }
}
