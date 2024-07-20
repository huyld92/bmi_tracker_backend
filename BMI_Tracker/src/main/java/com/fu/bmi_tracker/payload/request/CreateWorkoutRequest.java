/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
public class CreateWorkoutRequest {

    @NotNull
    @Size(min = 3, max = 100) // Đảm bảo tên bài tập không được null, độ dài từ 3 đến 100 ký tự
    @Schema(name = "workoutName", example = "Runing in one hour")
    private String workoutName;

    @Size(max = 255) // Mô tả bài tập có độ dài tối đa 200 ký tự
    @Schema(name = "workoutDescription", example = "Can be null")
    private String workoutDescription;

    @Schema(example = "80")
    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be a positive number")
    private Integer standardWeight;
}
