/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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
public class UpdateIngredientRequest {

    @Schema(example = "123")
    @NotNull(message = "Ingredient ID is required")
    private Integer ingredientID;

    @Schema(example = "Tomato")
    @NotBlank(message = "Ingredient name is required")
    private String ingredientName;

    @Schema(example = "http://example.com/photo.jpg")
    private String ingredientPhoto;

    @Schema(example = "Vitamin C, Fiber")
    private String nutritionalInformation;

    @Schema(example = "100.0")
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be a positive number")
    private Float quantity;

    @Schema(example = "grams")
    @NotBlank(message = "Unit is required")
    private String unit;

    @Schema(example = "18")
    @NotNull(message = "Ingredient calories are required")
    @PositiveOrZero(message = "Ingredient calories must be zero or a positive number")
    private Integer ingredientCalories;

    @Schema(example = "1")
    private Integer tagID;

}
