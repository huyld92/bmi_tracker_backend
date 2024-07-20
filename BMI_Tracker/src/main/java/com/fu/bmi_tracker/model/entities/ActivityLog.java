/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.payload.request.CreateActivityLogRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Duc Huy
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ActivityLog")
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ActivityLogID", nullable = false)
    private Integer activityLogID;

    @Column(name = "ActivityName", nullable = false)
    private String activityName;

    @Column(name = "CaloriesBurned", nullable = true)
    private Integer caloriesBurned;

    @Column(name = "Emoji", nullable = true)
    private String emoji;

    @Column(name = "Duration", nullable = false)
    private Integer duration;

    @Column(name = "RecordID", nullable = false)
    private Integer recordID;

    @Column(name = "ExerciseID", nullable = true)
    private Integer exerciseID;

    public ActivityLog(CreateActivityLogRequest activityLogRequest, Integer recordID) {
        this.activityName = activityLogRequest.getActivityName();
        this.emoji = activityLogRequest.getEmoji();
        this.caloriesBurned = activityLogRequest.getCaloriesBurned();
        this.duration = activityLogRequest.getDuration();
        this.exerciseID = activityLogRequest.getExerciseID();
        this.recordID = recordID;
        this.exerciseID = activityLogRequest.getExerciseID();
    }

}
