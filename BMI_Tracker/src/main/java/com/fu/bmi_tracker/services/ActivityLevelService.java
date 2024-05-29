/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.ActivityLevel;
import com.fu.bmi_tracker.payload.response.ActivityLevelResponse;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface ActivityLevelService extends GeneralService<ActivityLevel> {

    public List<ActivityLevelResponse> findAllActivityLevelsWithDetails();

}
