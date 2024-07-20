/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.ActivityLog;
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
public class ActivityLogResponse {

    private Integer activityLogID;
    private String activityName;
    private Integer caloriesBurned;
    private String emoji;
    private Integer duration;
    private Integer exerciseID;

    public ActivityLogResponse(ActivityLog activityLog) {
        this.activityLogID = activityLog.getActivityLogID();
        this.activityName = activityLog.getActivityName();
        this.caloriesBurned = activityLog.getCaloriesBurned();
        this.emoji = activityLog.getEmoji();
        this.duration = activityLog.getDuration();
        this.exerciseID = activityLog.getExerciseID();
    }

}
