/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UpdateFoodRequest {

    @NotNull
    @Schema(description = "ID of the food", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer foodID;

    @NotNull
    @Schema(description = "Name of the food", requiredMode = Schema.RequiredMode.REQUIRED)
    private String foodName;

    @NotNull
    @Schema(description = "Calories of the food", requiredMode = Schema.RequiredMode.REQUIRED)
    private int foodCalories;

    @Schema(description = "Description of the food (NULL)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String description;

    @Schema(description = "URL of the food photo(NULL)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String foodPhoto;

    @Schema(description = "URL of the food video(NULL)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String foodVideo;

    @NotNull
    @Min(1)
    @Max(10)
    @Schema(description = "Serving information of the food", requiredMode = Schema.RequiredMode.REQUIRED)
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
    @Schema(description = "Time required to prepare the food (in minutes)", requiredMode = Schema.RequiredMode.REQUIRED)
    private int foodTimeProcess;

//    @NotNull
//    @Schema(description = "Status indicating whether the food is active or not", requiredMode = Schema.RequiredMode.REQUIRED)
//    private Boolean isActive;
    @Schema(example = "[1,2,3]")
    private List<Integer> tagIDs;

//    private List<UpdateFoodRecipeRequest> recipeRequests;
}
