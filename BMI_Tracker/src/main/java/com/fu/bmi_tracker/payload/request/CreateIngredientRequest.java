/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
public class CreateIngredientRequest {

    @NotBlank(message = "Ingredient name is required")
    @Size(max = 100, message = "Ingreadient name must not exceed 100 characters")
    @Schema(name = "ingredientName", example = "Salt")
    private String ingredientName;

    @NotBlank(message = "Unit of measurement is required")
    @Size(max = 20, message = "Ingreadient unit must not exceed 20 characters")
    @Schema(name = "unit", example = "10 Gram")
    private String unit;

    @NotNull
    @Positive(message = "Quantity with default value")
    @Schema(name = "quantity", minContains = 0, example = "0")
    private Float quantity;

    @NotNull
    @Positive(message = "Ingredient calories must be positive")
    @Schema(name = "ingredientCalories", minContains = 0, example = "0")
    private Integer ingredientCalories;

    @NotNull
    @Positive(message = "TagID must be positive")
    @Schema(name = "tagID", example = "17")
    private Integer tagID;

    @NotBlank(message = "Ingredient photo url is required")
    @Schema(name = "ingredientPhoto", example = "photourl.com")
    private String ingredientPhoto;

}
