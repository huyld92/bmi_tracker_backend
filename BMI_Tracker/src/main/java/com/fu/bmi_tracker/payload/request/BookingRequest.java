/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
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
public class BookingRequest {

    @Schema(example = "Booking for subscription plan")
    @NotBlank
    @Size(max = 255)
    private String description;

    @Schema(example = "99.99")
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.00", message = "Amount must be greater than or equal to 0.00")
    private BigDecimal amount;

    @Schema(example = "1")
    @NotNull
    private Integer advisorID;

    @Schema(example = "1")
    @NotNull
    @Positive
    private Integer planID;

    @Schema(example = "30")
    @NotNull
    @Positive
    private Integer planDuration;

    @Schema(example = "323232323256")
    @NotBlank
    private String bookingNumber;
}
