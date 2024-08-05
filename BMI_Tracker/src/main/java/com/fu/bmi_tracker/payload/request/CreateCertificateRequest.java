/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
public class CreateCertificateRequest {

    @NotBlank
    @Size(max = 100)
    @Schema(name = "certificateName", example = "Certified Nutrition Specialist")
    private String certificateName;

    @NotBlank
    @Schema(name = "certificateLink", example = "certificatePhoto.jpg")
    private String certificateLink;

    @Positive
    @Schema(name = "advisorID", example = "1")
    private Integer advisorID;
}
