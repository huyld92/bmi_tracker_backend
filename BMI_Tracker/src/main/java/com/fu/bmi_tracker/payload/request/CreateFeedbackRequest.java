/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CreateFeedbackRequest {

    @NotNull
    @Size(max = 100)
    @Schema(name = "title", example = "Các món ăn quá nhiều dầu mỡ")
    private String title;

    @Size(max = 100)
    @Schema(name = "description", example = "Tui không thích ăn dầu mỡ (NULL)")
    private String description;

    @NotNull
    @Size(max = 100)
    @Schema(name = "type", example = "Menu feedback")
    private String type;

    @NotNull
    @Schema(name = "memberID", example = "1")
    private Integer memberID;

}
