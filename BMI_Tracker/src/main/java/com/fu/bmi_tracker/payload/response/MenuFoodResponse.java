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
public class MenuFoodResponse {

    private FoodResponse food;
    private EMealType mealType;
    private Boolean isActive;

//    public MenuFoodResponse(Food food, EMealType mealType) {
//        this.food = new FoodResponse(food);
//        this.mealType = mealType;
//    }

    public MenuFoodResponse(Food food, EMealType mealType, Boolean isActive) {
        this.food = new FoodResponse(food);
        this.mealType = mealType;
        this.isActive = isActive;
    }

}
