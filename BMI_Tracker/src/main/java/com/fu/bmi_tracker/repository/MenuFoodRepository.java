/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.MenuFood;
import com.fu.bmi_tracker.model.enums.EMealType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface MenuFoodRepository extends JpaRepository<MenuFood, Integer> {

    @Query("SELECT mf.food FROM MenuFood mf WHERE mf.menu.menuID = :menuID AND mf.mealType = :mealType")
    public List<Food> findFoodByMenu_MenuIDAndMealType(Integer menuID, EMealType mealType);

}
