/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.MenuFood;
import com.fu.bmi_tracker.model.enums.EMealType;
import com.fu.bmi_tracker.payload.response.MenuFoodResponse;
import com.fu.bmi_tracker.repository.FoodRepository;
import com.fu.bmi_tracker.repository.MenuFoodRepository;
import com.fu.bmi_tracker.repository.MenuRepository;
import com.fu.bmi_tracker.services.MenuFoodService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuFoodServiceImpl implements MenuFoodService {

    @Autowired
    MenuFoodRepository menuFoodRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    FoodRepository foodRepository;

    @Override
    public List<Food> findFoodByMenu_MenuIDAndMealType(Integer menuID, EMealType mealType) {
        return menuFoodRepository.findFoodByMenu_MenuIDAndMealType(menuID, mealType);
    }

    @Override
    public Iterable<MenuFood> findAll() {
        return menuFoodRepository.findAll();
    }

    @Override
    public Optional<MenuFood> findById(Integer id) {
        // return menuFoodRepository.findById(id);
        return null;
    }

    @Override
    public MenuFood save(MenuFood t) {
        return menuFoodRepository.save(t);
    }

    @Override
    public List<Food> findFoodByMenu_MenuID(Integer menuID) {
        return menuFoodRepository.findFoodByMenu_MenuID(menuID);
    }

    @Override
    public void saveAll(List<MenuFood> menuFoods) {
        menuFoodRepository.saveAll(menuFoods);
    }

    @Override
    public List<MenuFoodResponse> getMenuFoodResponse(Integer menuID) {
        return menuFoodRepository.findAllFoodAndMealTypeByMenuId(menuID);
    }

    @Override
    public List<Integer> getAllFoodIDsByMenuID(Integer menuID) {
        return menuFoodRepository.findAllFoodIDsByMenuID(menuID);
    }

    @Override
    public void updateMealTypeByMenuIdAndFoodId(EMealType mealType, Integer foodID, Integer menuID) {
        menuFoodRepository.updateMealTypeByMenuIdAndFoodId(mealType, menuID, foodID);
    }

    @Override
    public void deleteByMenuIdAndFoodId(Integer menuId, Integer foodId) {
        menuFoodRepository.deleteByMenuIdAndFoodId(menuId, foodId);
    }

    @Override
    public void deactivateMenuFood(Integer menuFoodID) {
        MenuFood menuFood = menuFoodRepository.findById(menuFoodID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find menu food id{" + menuFoodID + "}!"));

        // nếu menu đang false không cần cập nhật
        if (!menuFood.getIsActive()) {
            return;
        }

        // tính tổng calories
        int totalCalories = menuFood.getMenu().getTotalCalories() - menuFood.getFood().getFoodCalories();
        
        // cập nhật total calories của food
        menuFood.getMenu().setTotalCalories(totalCalories);
        menuFood.setIsActive(Boolean.FALSE);

        // deactivate menu food
        menuFoodRepository.save(menuFood);
    }

    @Override
    public void activateMenuFood(Integer menuFoodID) {
        MenuFood menuFood = menuFoodRepository.findById(menuFoodID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find menu food id{" + menuFoodID + "}!"));

        if (menuFood.getIsActive()) // tính tổng calories
        {
            return;
        }

        int totalCalories = menuFood.getMenu().getTotalCalories() + menuFood.getFood().getFoodCalories();

        menuFood.getMenu().setTotalCalories(totalCalories);
        menuFood.setIsActive(Boolean.TRUE);
        // cập nhật total calories của food
//        menuFood.getMenu().setTotalCalories(totalCalories);
//        menuRepository.save(menuFood.getMenu());
        // deactivate menu food
        menuFoodRepository.save(menuFood);
    }

}
