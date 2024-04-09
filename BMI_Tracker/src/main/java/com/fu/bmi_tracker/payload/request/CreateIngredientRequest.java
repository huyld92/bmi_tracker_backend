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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Duc Huy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIngredientRequest {

    @NotBlank(message = "Ingredient name is required")
    @Size(max = 2, message = "Ingreadient name must not exceed 100 characters")
    @Schema(name = "ingredientName", example = "Salt")
    private String ingredientName;

    @NotBlank(message = "Unit of measurement is required")
    @Size(max = 100, message = "Ingreadient name must not exceed 100 characters")
    @Schema(name = "unitOfMeasurement", example = "10 Gram")
    private String unitOfMeasurement;

    @NotNull
    @Positive(message = "Ingredient calories must be positive")
    @Schema(name = "ingredientCalories", minContains = 0, example = "0")
    private int ingredientCalories;

    @NotBlank(message = "Ingredient type is required")
    @Size(max = 100, message = "Ingreadient type must not exceed 100 characters")
    @Schema(name = "ingredientType", example = "Spices and Herbs")
    private String ingredientType;

    @NotBlank(message = "Ingredient photo url is required")
    @Size(max = 100, message = "Ingreadient photo url must not exceed 100 characters")
    @Schema(name = "ingredientPhotoUrl", example = "photourl.com")
    private String ingredientPhotoUrl;

}
