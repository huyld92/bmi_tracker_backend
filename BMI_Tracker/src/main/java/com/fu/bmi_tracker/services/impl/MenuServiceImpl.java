/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.Menu;
import com.fu.bmi_tracker.model.entities.MenuFood;
import com.fu.bmi_tracker.payload.request.CreateMenuFoodRequest;
import com.fu.bmi_tracker.payload.response.CountMenuResponse;
import com.fu.bmi_tracker.repository.FoodRepository;
import com.fu.bmi_tracker.repository.MenuFoodRepository;
import com.fu.bmi_tracker.services.MenuService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.repository.MenuRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    
    @Autowired
    MenuRepository menuRepository;
    
    @Autowired
    FoodRepository foodRepository;
    
    @Autowired
    MenuFoodRepository menuFoodRepository;
    
    @Override
    public Iterable<Menu> findAll() {
        return menuRepository.findAll();
    }
    
    @Override
    public Optional<Menu> findById(Integer id) {
        return menuRepository.findById(id);
    }
    
    @Override
    public Menu save(Menu t) {
        return menuRepository.save(t);
    }
    
    @Override
    public Iterable<Menu> getAllByAdvisorID(Integer advisorID) {
        return menuRepository.findByAdvisor_AdvisorID(advisorID);
    }
    
    @Override
    public Menu createNewMenu(Menu menu) {
        // lưu trữ Menu
        return save(menu);
    }
    
    @Override
    public List<CountMenuResponse> countTotalMenuIn6Months() {
        // lấy tất cả các commission trước ngày hiện tại trong vòng 6 tháng
        LocalDate startDate = LocalDate.now().minusMonths(6).withDayOfMonth(1);
        LocalDate endDate = LocalDate.now();
        return menuRepository.countTotalMenuPerMonthInBetween(startDate, endDate);
    }
    
    @Override
    public MenuFood createNewMenuFood(CreateMenuFoodRequest menuFoodRequest) {
        // gọi menuRepository tìm Menu
        Menu menu = menuRepository.findById(menuFoodRequest.getMenuID())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find menu id{" + menuFoodRequest.getMenuID() + "}!"));

        // tìm Food repository
        Food food = foodRepository.findById(menuFoodRequest.getFoodID())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find food id{" + menuFoodRequest.getFoodID() + "}!"));

        // tính calories
        int totalCalories = menu.getTotalCalories() + food.getFoodCalories();
        menu.setTotalCalories(totalCalories);
        menuRepository.save(menu);

        // tạo Menu food
        MenuFood menuFood = new MenuFood(menu, food, menuFoodRequest.getMealType(), Boolean.TRUE);

        // gọi menuFoodRepository lưu menu food
        return menuFoodRepository.save(menuFood);
    }
    
}
