/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.MealLog;
import com.fu.bmi_tracker.model.enums.EMealType;
import com.fu.bmi_tracker.payload.request.UpdateMealLogRequest;

/**
 *
 * @author Duc Huy
 */
public interface MealLogService extends GeneralService<MealLog> {

    public Iterable<MealLog> findByRecordID(Integer recordID);

    public Iterable<MealLog> findByRecordIDAndByMealType(Integer recordID, EMealType mealType);

    public void deleteById(int mealLogID);

    public MealLog updateMealLog(UpdateMealLogRequest mealLogRequest);

}
