/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import com.fu.bmi_tracker.model.enums.EPaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
public class UpdateCommissionRequest {

    @Schema(example = "123")
    @NotNull
    private Integer commissionID;

    @Schema(example = "2024-07-04T14:30:00 (NULL)")
    @PastOrPresent(message = "Paid date must be in the past or present")
    private LocalDateTime paidDate;

    @Schema(example = "1500.00")
    @PositiveOrZero(message = "Paid amount must be zero or a positive number")
    private BigDecimal paidAmount;

    @Schema(example = "PAID")
    @NotNull(message = "Payment status is required")
    private EPaymentStatus paymentStatus;

    @Schema(example = "NULL")
    @Size(max = 255, message = "Commission description must be less than 255 characters")
    private String commissionDescription;

}
