/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import com.fu.bmi_tracker.model.enums.EMealType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class CreateMealLogRequest {

    @Size(max = 100)
    @Schema(name = "foodName", example = "Pizza")
    private String foodName;

    @NotNull
    @Positive
    @Schema(name = "calories", example = "500")
    private Integer calories;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Schema(name = "mealType", example = "Breakfast")
    private EMealType mealType;

    @NotBlank
    @Schema(name = "dateOfMeal", example = "2024-05-01")
    private String dateOfMeal;

    @Schema(name = "foodID", example = "1", nullable = true)
    @Positive
    private Integer foodID;

    @Schema(name = "quantity", example = "1", nullable = true)
    @Positive
    private Float quantity;

    @Size(max = 50, message = "Unit must not exceed 50 characters")
    @Schema(name = "unit", example = "bowl (250g)")
    private String unit;
}
