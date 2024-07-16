/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
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

    @NotNull
    @Min(0) // Tổng lượng calo đốt cháy không được null và phải lớn hơn hoặc bằng 0
    @Schema(name = "totalCaloriesBurned", example = "500")
    private Integer totalCaloriesBurned;

    @NotEmpty
    @Schema(name = "exerciseIDs", example = "[1,2,3]")
    private List<Integer> exerciseIDs;

}
