/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class FindOrderByAdvisorByMonthRequest {

    @Schema(example = "1", description = "Unique identifier of the advisor")
    @NotNull(message = "Advisor ID cannot be null")
    private Integer advisorID;

    @Schema(example = "2024-06", description = "Month for which orders are requested")
    @NotNull
    @Pattern(regexp = "\\d{4}-\\d{2}", message = "Month must be in yyyy-MM format")
    private String month;

}
