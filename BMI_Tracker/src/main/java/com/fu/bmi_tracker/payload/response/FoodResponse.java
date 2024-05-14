/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.FoodTag;
import com.fu.bmi_tracker.model.entities.Recipe;
import com.fu.bmi_tracker.model.entities.Tag;
import com.fu.bmi_tracker.model.entities.Trainer;
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
public class FoodResponse {

    private int foodID;

    private String foodName;

    private int foodCalories;

    private String description;

    private String foodPhoto;

    private String foodVideo;

    private int foodTimeProcess;

    private String status;

    private Trainer trainer;

    private List<Tag> foodTags;

    private List<Recipe> recipes;

    public FoodResponse(Food foodSave, List<Tag> foodTags, List<Recipe> recipes) {
        this.foodID = foodSave.getFoodID();
        this.foodName = foodSave.getFoodName();
        this.foodCalories = foodSave.getFoodCalories();
        this.description = foodSave.getDescription();
        this.foodPhoto = foodSave.getFoodPhoto();
        this.foodVideo = foodSave.getFoodVideo();
        this.foodTimeProcess = foodSave.getFoodTimeProcess();
        this.status = foodSave.getIsActive() ? "Active" : "Inative";
        this.trainer = foodSave.getTrainer();
        this.foodTags = foodTags;
        this.recipes = recipes;
    }
}
