/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.ActivityLog;
import com.fu.bmi_tracker.payload.request.CreateActivityLogRequest;
import com.fu.bmi_tracker.payload.request.UpdateActivityLogRequest;
import java.time.LocalDate;

/**
 *
 * @author Duc Huy
 */
public interface ActivityLogService extends GeneralService<ActivityLog> {

    public Iterable<ActivityLog> findByRecordID(Integer recordID);

    public void deleteById(int activityLogID);

    public ActivityLog createActivityLog(CreateActivityLogRequest activityLogRequest, LocalDate dateOfActivity, Integer accountID);

    public ActivityLog updateActivityLog(UpdateActivityLogRequest activityLogRequest);


}
