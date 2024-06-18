/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.DailyRecord;
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
public class DailyRecordResponse {

    private Integer recordID;
    private Integer totalCaloriesIn;
    private Integer totalCaloriesOut;
    private Integer defaultCalories;
    private LocalDate date;

    public DailyRecordResponse(DailyRecord dr) {
        this.recordID = dr.getRecordID();
        this.totalCaloriesIn = dr.getTotalCaloriesIn();
        this.totalCaloriesOut = dr.getTotalCaloriesOut();
        this.defaultCalories = dr.getDefaultCalories();
        this.date = dr.getDate();
    }
}
