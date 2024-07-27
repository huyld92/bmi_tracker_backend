package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Duc Huy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateExerciseRequest {

    @Schema(example = "Running")
    @NotNull(message = "Exercise name is required")
    private String exerciseName;

    @Schema(example = "http://example.com/photo.jpg")
    private String exercisePhoto;

    @Schema(example = "http://example.com/video.mp4")
    private String exerciseVideo;

    @Schema(example = "8.0")
    @NotNull(message = "MET is required")
    @PositiveOrZero(message = "MET must be a positive number")
    private Float met;

    @Schema(example = "A description of the exercise")
    @Size(max = 255, message = "Exercise description must be less than 255 characters")
    private String exerciseDescription;

    @NotNull
    @Positive(message = "TagID must be positive")
    @Schema(name = "tagID", example = "17")
    private Integer tagID;
}
