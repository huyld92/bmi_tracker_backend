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
import java.time.LocalDate;
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
public class CreateOderRequest {

    @Schema(example = "Order for subscription service")
    @NotBlank
    @Size(max = 255)
    private String description;

    @Schema(example = "99.99")
    @NotNull
    @Positive
    private Float amount;
//
//    @Schema(example = "2024-06-05T10:15:30")
//    @NotNull
//    private LocalDateTime dateOrder;
//
//    @Schema(example = "2024-06-01")
//    @NotNull
//    private LocalDate startDate;

//    @Schema(example = "2025-06-01")
//    @NotNull
//    private LocalDate endDate;
//    @Schema(example = "1")
//    @NotNull
//    private Integer memberID;
    @Schema(example = "1")
    @NotNull
    private Integer advisorID;

//    @Schema(example = "1")
//    @NotNull
//    private Integer transactionID;
    @Schema(example = "30")
    @NotNull
    @Positive
    private Integer planDuration;
}
