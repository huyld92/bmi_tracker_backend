/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.MealLog;
import com.fu.bmi_tracker.model.enums.EMealType;
import java.time.LocalDate;

/**
 *
 * @author Duc Huy
 */
public interface MealLogService extends GeneralService<MealLog> {

    public Iterable<MealLog> findAllByDateOfMeal(LocalDate dateOfMeal);

    public Iterable<MealLog> findAllByDateOfMealAndMember_MemberID(LocalDate dateOfMeal, int memberID);

    public Iterable<Integer> findFoodIDByDateOfMealAndMemberIDAndMealType(LocalDate dateOfMeal, int memberID, EMealType mealType);

    public void deleteById(int mealLogID);

}
