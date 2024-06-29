/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class UpdateExercerRequest {

    @Schema(example = "1")
    @NotNull(message = "Exercise name is required")
    private Integer exerciseID;

    @Schema(example = "Running")
    @NotBlank(message = "Exercise name is required")
    private String exerciseName;

    @Schema(example = "🏃", description = "can NULL")
    private String emoji;

    @Schema(example = "60")
    @NotNull(message = "Duration is required")
    @PositiveOrZero(message = "Duration must be a positive number or zero")
    private Integer duration;

    @Schema(example = "60")
    private Float distance;

    @Schema(example = "300")
    @NotNull(message = "Calories burned is required")
    @PositiveOrZero(message = "Calories burned must be a positive number or zero")
    private Integer caloriesBurned;
    
    @NotNull
    @Schema(example = "[1,2,3]")
    private List<Integer> tagIDs;
//    @NotNull
//    @Schema(example = "true")
//    private Boolean isActive;
}
