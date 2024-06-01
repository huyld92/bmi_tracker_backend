/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
public class CreateFoodRequest {

    @NotBlank
    @Size(max = 100)
    @Schema(name = "foodName", example = "Pizza")
    private String foodName;

    @NotNull
    @Positive
    @Schema(name = "foodCalories", example = "500")
    private Integer foodCalories;

    @NotBlank
    @Size(max = 255)
    @Schema(name = "description", example = "Delicious pizza with cheese and toppings.")
    private String description;

    @NotBlank
    @Size(max = 255)
    @Schema(name = "foodPhoto", example = "photourl.com/pizza.jpg")
    private String foodPhoto;

    @NotBlank
    @Size(max = 255)
    @Schema(name = "foodVideo", example = "videourl.com/pizza.mp4")
    private String foodVideo;
    
    @NotBlank
    @Size(max = 255)
    @Schema(name = "foodNutrition", example = "100 Protein, 20 Carbs, 200 Fat")
    private String foodNutrition;

    @NotNull
    @Positive
    @Schema(name = "foodTimeProcess", example = "30")
    private Integer foodTimeProcess;

    @NotEmpty
    @Schema(name = "tagIDs", example = "[1, 2, 3]")
    private List<Integer> tagIDs;

    @NotEmpty
    @Schema(name = "recipeRequests")
    private List<RecipeRequest> recipeRequests;

}
