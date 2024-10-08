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
public class CreateMemberRequest { 
    
    @NotNull
    private String dietaryPreference;

    @NotNull
    @Positive
    @Schema(name = "height", example = "180")
    private Integer height;

    @NotNull
    @Positive
    @Schema(name = "weight", example = "75")
    private Integer weight;

    @NotNull
    @Positive
    @Schema(name = "targetWeight", example = "70")
    private Integer targetWeight; 
    
    @NotNull
    @Positive
    @Schema(name = "activityLevelID", example = "1")
    private Integer activityLevelID;
}
