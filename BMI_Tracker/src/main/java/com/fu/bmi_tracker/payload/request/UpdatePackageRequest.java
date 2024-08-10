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
 * @author BaoLG
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePackageRequest {

    @Positive
    @Schema(name = "packageID", example = "1")
    private Integer packageID;

    @NotNull
    @Positive
    @Schema(name = "packageName", example = "Kế hoạch giảm cân trong 30 ngày")
    private String packageName;

    @Schema(example = "99.99")
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.00", message = "Price must be greater than or equal to 0.00")
    private BigDecimal price;

    @NotBlank
    @Size(max = 255)
    @Schema(name = "description", example = "Kế hoạch bao gồm thực đơn đề cử, và các excersie để đạt được kết quả tốt nhất trong 30 ngày.")
    private String description;

    @NotNull
    @Positive
    @Schema(name = "packageDuration", example = "30")
    private Integer packageDuration;
}
