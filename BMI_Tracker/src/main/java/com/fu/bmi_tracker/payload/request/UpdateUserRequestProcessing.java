/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import com.fu.bmi_tracker.model.enums.EStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UpdateUserRequestProcessing {

    @NotNull
    private Integer userRequestID;

    @NotBlank(message = "Process Note is mandatory")
    @Size(min = 5, max = 255, message = "Process Note must be between 5 and 255 characters")
    private String processNote;

    @NotNull(message = "Status is required")
    @Schema(name = "status", example = "REJECTED")
    private EStatus status;

}
