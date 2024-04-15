/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class CreateGoalRequest {

    @NotBlank(message = "Goal name is required")
    @Size(max = 100, message = "Ingreadient name must not exceed 100 characters")
    @Schema(name = "goalName", example = "Weight loss")
    private String goalName;

    @Size(max = 100, message = "Ingreadient name must not exceed 100 characters")
    @Schema(name = "description", example = "The goal is to lose weight")
    private String description;

}
