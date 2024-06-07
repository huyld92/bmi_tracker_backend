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
 * @author BaoLG
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePlanRequest {
    
    
    @Positive
    @Schema(name = "planID", example = "1")
    private int planID;
    
    @NotNull
    @Positive
    @Schema(name = "planName", example = "Kế hoạch giảm cân trong 30 ngày")
    private String planName;
    
    @NotNull
    @Positive
    @Schema(name = "price", example = "18000000")
    private float price;
    
    @NotBlank
    @Size(max = 255)
    @Schema(name = "description", example = "Kế hoạch bao gồm thực đơn đề cử, và các excersie để đạt được kết quả tốt nhất trong 30 ngày.")
    private String description;
    
    @NotNull
    @Positive
    @Schema(name = "planDuration", example = "30")
    private int planDuration;

}
