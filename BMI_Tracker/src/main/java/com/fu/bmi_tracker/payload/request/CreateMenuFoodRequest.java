/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import com.fu.bmi_tracker.model.enums.EMealType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class CreateMenuFoodRequest {

    @NotNull
    @Min(1)
    @Schema(example = "1" , description = "Unique identifier for the menu")
    private Integer menuID;

    @NotNull
    @Min(1)
    @Schema(example = "1" , description = "Unique identifier for the food")
    private Integer foodID;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Schema(description = "Type of meal (Breakfast,Lunch, Dinner, Snack)", example = "Breakfast")
    private EMealType mealType;

}
