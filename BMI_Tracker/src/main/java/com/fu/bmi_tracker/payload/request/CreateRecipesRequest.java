/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class CreateRecipesRequest {

    @Schema(example = "123")
    @NotNull(message = "Food ID is required")
    private Integer foodID;

    @Schema(example = "[1, 2, 3]")
    @NotNull(message = "Ingredient IDs are required")
    private List<@NotNull Integer> ingredientIDs;
}
