/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
public class CreateMenuRequest {

    @Schema(example = "Healthy Breakfast")
    @NotBlank(message = "Menu name is required")
    private String menuName;

    @Schema(example = "http://example.com/photo.jpg (NULL)")
    private String menuPhoto;

    @Schema(example = "A delicious and healthy breakfast option.")
    private String menuDescription;

    @Schema(example = "450")
    @NotNull(message = "Total calories are required")
    @PositiveOrZero(message = "Total calories must be zero or positive")
    private Integer totalCalories;

    @Schema(description = "List of foods included in the menu")
    @NotNull(message = "Menu foods are required")
    private List<@NotNull MenuFoodRequest> menuFoods;

    @NotEmpty
    @Schema(name = "tagIDs", example = "[1, 2, 3]")
    private List<Integer> tagIDs;
}
