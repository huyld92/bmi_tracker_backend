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

    private String foodNutrition;

    private Integer foodTimeProcess;

    private Integer serving;

    private LocalDate creationDate;

    private Boolean isActive;

    private List<TagBasicResponse> foodTags;

    public FoodResponseAll(Food foodSaved, List<TagBasicResponse> foodTags) {
        this.foodID = foodSaved.getFoodID();
        this.foodName = foodSaved.getFoodName();
        this.foodCalories = foodSaved.getFoodCalories();
        this.description = foodSaved.getDescription();
        this.foodPhoto = foodSaved.getFoodPhoto();
        this.foodVideo = foodSaved.getFoodVideo();
        this.foodNutrition = foodSaved.getFoodNutrition();
        this.foodTimeProcess = foodSaved.getFoodTimeProcess();
        this.creationDate = foodSaved.getCreationDate();
        this.serving = foodSaved.getServing();
        this.isActive = foodSaved.getIsActive();
        this.foodTags = foodTags;
    }
}
