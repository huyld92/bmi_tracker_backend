/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class RecipeRequest {

    @Schema(example = "1")
    @NotNull(message = "Ingredient ID is required")
    private Integer ingredientID;

    @Schema(example = "grams")
    @NotBlank(message = "Unit is required")
    private String unit;

    @Schema(example = "100")
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be a positive number")
    private Float quantity;

}
