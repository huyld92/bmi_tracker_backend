/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.MealLog;
import com.fu.bmi_tracker.model.enums.EMealType;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface MealLogRepository extends JpaRepository<MealLog, Integer> {

    public Iterable<MealLog> findAllByDateOfMeal(LocalDate dateOfMeal);

    public Iterable<MealLog> findAllByDateOfMealAndUser_UserID(LocalDate dateOfMeal, int userID);

    @Query("SELECT m.food.foodID FROM MealLog m WHERE m.dateOfMeal = :dateOfMeal AND m.user.userID = :userID AND m.mealType = :mealType")
    public Iterable<Integer> findFoodIDByDateOfMealAndUser_UserIDAndMealType(LocalDate dateOfMeal, int userID, EMealType mealType);

}
