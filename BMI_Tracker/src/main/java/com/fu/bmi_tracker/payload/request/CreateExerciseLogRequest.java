/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class CreateExerciseLogRequest {

    @Size(max = 100)
    @Schema(name = "exerciseName", example = "Rope skipping")
    private String exerciseName;

    @Positive
    @Schema(name = "caloriesBurned", example = "500", nullable = true)
    private Integer caloriesBurned;

    @NotNull
    @Positive
    @Schema(name = "duaration", example = "1")
    private Integer duaration;

    @NotNull
    @Positive
    @Schema(name = "recordID", example = "1")
    private Integer recordID;
}
