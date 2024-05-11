/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import com.fu.bmi_tracker.model.enums.EMealType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class CreateMealLogRequest {

    @Size(max = 100)
    @Schema(name = "foodName", example = "Pizza")
    private String foodName;

    @NotNull
    @Positive
    @Schema(name = "calories", example = "500")
    private Integer calories;

    @NotNull
    @PastOrPresent
    @Schema(name = "dateOfMeal", example = "2024-04-17")
    private LocalDate dateOfMeal;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Schema(name = "mealType", example = "Breakfast")
    private EMealType mealType;

    @NotBlank
    @Size(max = 50)
    @Schema(name = "quantity", example = "1 slice")
    private String quantity;

    @NotNull
    @Positive
    @Schema(name = "customerID", example = "1")
    private Integer customerID;

    @Positive
    @Schema(name = "foodID", example = "1")
    private Integer foodID;

}
