/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

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
public class WorkoutHistoryResponse {

    private Integer workoutHistoryID;
    private LocalDate dateOfAssigned;
    private Integer workoutID;
    private String workoutName;
    private String workoutDescription;
    private Integer standardWeight;
    private Integer totalCloriesBurned;
    private Integer memberID;
    private String fullName;
    private Boolean isActive;

}
