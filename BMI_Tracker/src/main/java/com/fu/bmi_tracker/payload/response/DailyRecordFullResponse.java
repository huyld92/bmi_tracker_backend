/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.DailyRecord;
import java.time.LocalDate;
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
public class DailyRecordFullResponse {

    private Integer recordID;
    private Integer totalCaloriesIn;
    private Integer totalCaloriesOut;
    private Integer defaultCalories;
    private LocalDate date;

    private List<ActivityLogResponse> activityLogs;
    private List<MealLogResponse> mealLogs;

    public DailyRecordFullResponse(DailyRecord dailyRecord, List<ActivityLogResponse> activityLogs, List<MealLogResponse> mealLogs) {
        this.recordID = dailyRecord.getRecordID();
        this.totalCaloriesIn = dailyRecord.getTotalCaloriesIn();
        this.totalCaloriesOut = dailyRecord.getTotalCaloriesOut();
        this.defaultCalories = dailyRecord.getDefaultCalories();
        this.date = dailyRecord.getDate();
        this.activityLogs = activityLogs;
        this.mealLogs = mealLogs;
    }

}
