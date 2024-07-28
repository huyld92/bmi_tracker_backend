/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.MenuFood;
import com.fu.bmi_tracker.model.enums.EMealType;
import com.fu.bmi_tracker.payload.response.MenuFoodResponse;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface MenuFoodService extends GeneralService<MenuFood> {

    public List<Food> findFoodByMenu_MenuIDAndMealType(Integer menuID, EMealType mealType);

    public List<Food> findFoodByMenu_MenuID(Integer menuID);

    public void saveAll(List<MenuFood> menuFoods);

    public List<MenuFoodResponse> getMenuFoodResponse(Integer menuID);

    public List<Integer> getAllFoodIDsByMenuID(Integer menuID);

    public void updateMealTypeByMenuIdAndFoodId(EMealType mealType, Integer foodID, Integer menuID);

    public void deleteByMenuFoodID(Integer menuFoodID);

    public void deactivateMenuFood(Integer menuFoodID);

    public void activateMenuFood(Integer menuFoodID);

}
