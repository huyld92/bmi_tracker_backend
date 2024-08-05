/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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

    @Size(max = 255)
    @Schema(name = "description", example = "Delicious pizza with cheese and toppings. (Can blank)")
    private String description;

    @Schema(name = "foodPhoto", example = "photourl.com/pizza.jpg (Can blank)")
    @NotBlank
    private String foodPhoto;

    @NotBlank
    @Schema(name = "foodVideo", example = "videourl.com/pizza.mp4 (Can blank)")
    private String foodVideo;

    @Schema(name = "serving", example = "1 serving")
    @Positive
    private Integer serving;

    @NotNull
    @Min(0)
    @Schema(name = "carbs", example = "10")
    private Float carbs;

    @NotNull
    @Min(0)
    @Schema(name = "protein", example = "10")
    private Float protein;

    @NotNull
    @Min(0)
    @Schema(name = "fat", example = "10")
    private Float fat;

    @NotNull
    @Positive
    @Schema(name = "foodTimeProcess", example = "30")
    private Integer foodTimeProcess;

    @Schema(name = "tagIDs", example = "[1, 2, 3]")
    private List<Integer> tagIDs;

    @NotEmpty
    private List<@Valid RecipeRequest> recipeRequests;

}
