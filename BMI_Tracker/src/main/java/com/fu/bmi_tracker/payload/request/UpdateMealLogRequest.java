/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class UpdateMealLogRequest {

    @NotNull
    @Positive
    @Schema(name = "mealLogID", example = "1")
    private Integer mealLogID;

    @NotNull
    @Positive
    @Schema(name = "calories", example = "500")
    private Integer calories;

    @NotNull
    @Schema(name = "quantity", example = "1")
    private Float quantity;

}
