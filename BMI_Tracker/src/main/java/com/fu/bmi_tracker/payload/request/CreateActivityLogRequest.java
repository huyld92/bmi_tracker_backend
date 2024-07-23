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
public class CreateActivityLogRequest {

    @Size(max = 100)
    @Schema(name = "activityName", example = "Rope skipping")
    private String activityName;

    @Size(max = 100)
    @Schema(name = "emoji", example = "📝")
    private String emoji;

    @Positive
    @Schema(name = "caloriesBurned", example = "500", nullable = true)
    private Integer caloriesBurned;

    @NotNull
    @Positive
    @Schema(name = "duration", example = "1")
    private Integer duration;

    @NotBlank
    @Schema(name = "dateOfActivity", example = "2024-05-01")
    private String dateOfActivity;

    @Positive
    @Schema(name = "exerciseID", example = "1")
    private Integer exerciseID;
}
