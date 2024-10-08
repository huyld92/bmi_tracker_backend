/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.MealLog;
import com.fu.bmi_tracker.model.enums.EMealType;
import com.fu.bmi_tracker.payload.response.MealLogResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface MealLogRepository extends JpaRepository<MealLog, Integer> {

    public Iterable<MealLog> findByRecordID(Integer recordID);

    public Iterable<MealLog> findByRecordIDAndMealType(Integer recordID, EMealType mealType);

    public List<MealLogResponse> findResponseByRecordID(Integer recordID);

}
