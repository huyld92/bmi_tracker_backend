/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank
    @Schema(description = "Nutritional information of the food", requiredMode = Schema.RequiredMode.REQUIRED)
    private String foodNutrition;

    @NotNull
    @Schema(description = "Serving information of the food", requiredMode = Schema.RequiredMode.REQUIRED)
    private String serving;

    @NotNull
    @Schema(description = "Time required to prepare the food (in minutes)", requiredMode = Schema.RequiredMode.REQUIRED)
    private int foodTimeProcess;

    @NotNull
    @Schema(description = "Date when the food was created", requiredMode = Schema.RequiredMode.REQUIRED, example = "2024-06-06")
    private LocalDate creationDate;

//    @NotNull
//    @Schema(description = "Status indicating whether the food is active or not", requiredMode = Schema.RequiredMode.REQUIRED)
//    private Boolean isActive;
    @Schema(example = "[1,2,3]")
    private List<Integer> tagIDs;

    private List<RecipeRequest> recipeRequests;
}
