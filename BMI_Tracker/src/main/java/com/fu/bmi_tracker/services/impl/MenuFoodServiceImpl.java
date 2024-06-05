/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.MenuFood;
import com.fu.bmi_tracker.model.enums.EMealType;
import com.fu.bmi_tracker.payload.response.MenuFoodResponse;
import com.fu.bmi_tracker.repository.MenuFoodRepository;
import com.fu.bmi_tracker.services.MenuFoodService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuFoodServiceImpl implements MenuFoodService {

    @Autowired
    MenuFoodRepository repository;

    @Override
    public List<Food> findFoodByMenu_MenuIDAndMealType(Integer menuID, EMealType mealType) {
        return repository.findFoodByMenu_MenuIDAndMealType(menuID, mealType);
    }

    @Override
    public Iterable<MenuFood> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<MenuFood> findById(Integer id) {
        // return repository.findById(id);
        return null;
    }

    @Override
    public MenuFood save(MenuFood t) {
        return repository.save(t);
    }

    @Override
    public List<Food> findFoodByMenu_MenuID(Integer menuID) {
        return repository.findFoodByMenu_MenuID(menuID);
    }

    @Override
    public void saveAll(List<MenuFood> menuFoods) {
        repository.saveAll(menuFoods);
    }

    @Override
    public List<MenuFoodResponse> getMenuFoodResponse(Integer menuID) {
        return repository.findAllFoodAndMealTypeByMenuId(menuID);
    }

    @Override
    public List<Integer> getAllFoodIDsByMenuID(Integer menuID) {
        return repository.findAllFoodIDsByMenuID(menuID);
    }

    @Override
    public void updateMealTypeByMenuIdAndFoodId(EMealType mealType, Integer foodID, Integer menuID) {
        repository.updateMealTypeByMenuIdAndFoodId(mealType, menuID, foodID);
    }

    @Override
    public void deleteByMenuIdAndFoodId(Integer menuId, Integer foodId) {
        repository.deleteByMenuIdAndFoodId(menuId, foodId);
    }

}
