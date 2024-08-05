/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class UpdateActivityLogRequest {

    @Positive
    @Schema(name = "activityID", example = "1")
    private Integer activityID;

    @Size(max = 100)
    @Schema(name = "activityName", example = "Rope skipping")
    private String activityName;

    @NotNull
    @Min(0)
    @Max(600)
    @Schema(name = "duration", example = "1")
    private Integer duration;

    @Positive
    @Schema(name = "caloriesBurned", example = "500", nullable = true)
    private Integer caloriesBurned;

}
