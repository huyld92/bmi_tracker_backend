/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.Tag;
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

    private int foodID;

    private String foodName;

    private int foodCalories;

    private String description;

    private String foodPhoto;

    private String foodVideo;

    private String foodNutrition;

    private int foodTimeProcess;

    private LocalDate creationDate;

    private boolean isActive;

    private List<Tag> foodTags;

    public FoodResponseAll(Food foodSaved, List<Tag> foodTags) {
        this.foodID = foodSaved.getFoodID();
        this.foodName = foodSaved.getFoodName();
        this.foodCalories = foodSaved.getFoodCalories();
        this.description = foodSaved.getDescription();
        this.foodPhoto = foodSaved.getFoodPhoto();
        this.foodVideo = foodSaved.getFoodVideo();
        this.foodNutrition = foodSaved.getFoodNutrition();
        this.foodTimeProcess = foodSaved.getFoodTimeProcess();
        this.foodTags = foodTags;
    }
}
