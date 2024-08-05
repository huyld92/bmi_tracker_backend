/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.util;

import com.fu.bmi_tracker.model.entities.FoodDetails;
import com.fu.bmi_tracker.payload.response.FoodDetailsResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Duc Huy
 */
public class FoodDetailsConverter {

    public static FoodDetailsResponse convertToFoodDetailsResponse(FoodDetails foodDetails) {
        FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse(foodDetails);

        return foodDetailsResponse;
    }

    public static List<FoodDetailsResponse> convertToFoodDetailsResponseList(List<FoodDetails> foodDetailses) {
        return foodDetailses.stream()
                .map(FoodDetailsConverter::convertToFoodDetailsResponse)
                .collect(Collectors.toList());
    }
}
