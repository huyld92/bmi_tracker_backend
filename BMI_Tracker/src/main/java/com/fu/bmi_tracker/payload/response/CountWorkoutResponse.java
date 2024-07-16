/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

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
public class CountWorkoutResponse {

    private String yearMonth;
    private Long totalWorkout;

    public CountWorkoutResponse(Integer year, Integer month, Long totalWorkout) {
        this.yearMonth = Integer.toString(year) + "-" + Integer.toString(month);
        this.totalWorkout = totalWorkout;
    }
}
