/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.MemberBodyMass;
import com.fu.bmi_tracker.util.BMIUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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
public class MemberBodyMassResponse {

    @Schema(example = "12345")
    private Integer memberBodyMassID;

    @Schema(example = "175")
    @NotNull(message = "Height is required")
    @Positive(message = "Height must be a positive number")
    private Integer height;

    @Schema(example = "70")
    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be a positive number")
    private Integer weight;

    @Schema(example = "25")
    @NotNull(message = "Age is required")
    @Positive(message = "Age must be a positive number")
    private Integer age;

    @Schema(example = "22.9")
    @NotNull(message = "BMI is required")
    @PositiveOrZero(message = "BMI must be zero or a positive number")
    private Double bmi;

    @Schema(example = "2024-06-16T14:30:00")
    @NotNull(message = "Date input is required")
    @PastOrPresent(message = "Date input must be in the past or present")
    private LocalDateTime dateInput;

    public MemberBodyMassResponse(MemberBodyMass bodyMass, int age, double bmi) {
        this.memberBodyMassID = bodyMass.getMemberBodyMassID();
        this.height = bodyMass.getHeight();
        this.weight = bodyMass.getWeight();
        this.age = age;
        this.bmi = bmi;
        this.dateInput = bodyMass.getDateInput();
    }

}
