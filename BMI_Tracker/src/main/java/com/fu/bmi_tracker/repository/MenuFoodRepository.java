/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.MenuFood;
import com.fu.bmi_tracker.model.enums.EMealType;
import com.fu.bmi_tracker.payload.response.MenuFoodResponse;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface MenuFoodRepository extends JpaRepository<MenuFood, MenuFood> {

    @Query("SELECT mf.food FROM MenuFood mf WHERE mf.menu.menuID = :menuID AND mf.mealType = :mealType")
    public List<Food> findFoodByMenu_MenuIDAndMealType(Integer menuID, EMealType mealType);

    @Query("SELECT mf.food FROM MenuFood mf WHERE mf.menu.menuID = :menuID")
    public List<Food> findFoodByMenu_MenuID(Integer menuID);

    @Query("SELECT new com.fu.bmi_tracker.payload.response.MenuFoodResponse(mf.food, mf.mealType) "
            + "FROM MenuFood mf "
            + "WHERE mf.menu.menuID = :menuId")
    List<MenuFoodResponse> findAllFoodAndMealTypeByMenuId(Integer menuId);

    @Query("SELECT mf.food.foodID FROM MenuFood mf WHERE mf.menu.menuID = :menuID")
    List<Integer> findAllFoodIDsByMenuID(Integer menuID);

    @Modifying
    @Transactional
    @Query("UPDATE MenuFood mf SET mf.mealType = :mealType WHERE mf.menu.menuID = :menuID AND mf.food.foodID = :foodID")
    void updateMealTypeByMenuIdAndFoodId(EMealType mealType, Integer menuID, Integer foodID);

    @Modifying
    @Transactional
    @Query("DELETE FROM MenuFood mf WHERE mf.menu.menuID = :menuID AND mf.food.foodID = :foodID")
    void deleteByMenuIdAndFoodId(Integer menuID, Integer foodID);

}
