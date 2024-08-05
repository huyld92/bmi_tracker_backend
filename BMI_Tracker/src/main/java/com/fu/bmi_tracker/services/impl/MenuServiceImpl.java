/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.Menu;
import com.fu.bmi_tracker.model.entities.MenuFood;
import com.fu.bmi_tracker.payload.request.CreateMenuFoodRequest;
import com.fu.bmi_tracker.payload.request.CreateMenuRequest;
import com.fu.bmi_tracker.payload.request.MenuFoodRequest;
import com.fu.bmi_tracker.payload.response.CountMenuResponse;
import com.fu.bmi_tracker.repository.AdvisorRepository;
import com.fu.bmi_tracker.repository.FoodRepository;
import com.fu.bmi_tracker.repository.MenuFoodRepository;
import com.fu.bmi_tracker.services.MenuService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.repository.MenuRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    AdvisorRepository advisorRepository;

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
    public Menu createNewMenu(CreateMenuRequest menuRequest, Integer accountID) {

        // Tìm advisor
        Advisor advisor = advisorRepository.findByAccount_AccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("No advisor found!"));

        // Tạo mới menu
        Menu menu = new Menu(menuRequest, advisor);

        // Tạo danh sách Menu Food object từ menuRequest và menu food reponse
        List<MenuFood> menuFoods = new ArrayList<>();
        menuRequest.getMenuFoods().forEach((MenuFoodRequest request) -> {
            Food food = foodRepository.findById(request.getFoodID())
                    .orElseThrow(() -> new EntityNotFoundException("Cannot find food id{" + request.getFoodID() + "}!"));

            //tính total calories
            int totalCalories = menu.getTotalCalories() + food.getFoodCalories();
            menu.setTotalCalories(totalCalories);

//            FoodResponse foodResponse = new FoodResponse(food);
//
//            // add menu food response
//            menuFoodResponses.add(new MenuFoodResponse(foodResponse, request.getMealType(), menu.getIsActive()));
            // add menu food
            menuFoods.add(new MenuFood(menu, food, request.getMealType()));
        });

        menu.setMenuFoods(menuFoods);
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
        MenuFood menuFood = new MenuFood(menu, food, menuFoodRequest.getMealType());

        // gọi menuFoodRepository lưu menu food
        return menuFoodRepository.save(menuFood);
    }

    @Override
    public List<MenuFood> createNewMenuFoods(List<CreateMenuFoodRequest> menuFoodRequests) {
        List<MenuFood> menuFoods = new ArrayList<>();
        menuFoodRequests.forEach(request -> {
            // gọi menuRepository tìm Menu
            Menu menu = menuRepository.findById(request.getMenuID())
                    .orElseThrow(() -> new EntityNotFoundException("Cannot find menu id{" + request.getMenuID() + "}!"));

            // tìm Food repository
            Food food = foodRepository.findById(request.getFoodID())
                    .orElseThrow(() -> new EntityNotFoundException("Cannot find food id{" + request.getFoodID() + "}!"));

            // tính calories
            int totalCalories = menu.getTotalCalories() + food.getFoodCalories();
            menu.setTotalCalories(totalCalories);
            menuRepository.save(menu);

            // tạo Menu food
            MenuFood menuFood = new MenuFood(menu, food, request.getMealType());
            menuFoods.add(menuFood);
        });
        return menuFoodRepository.saveAll(menuFoods);
    }

    @Override
    public Iterable<Menu> getMenuActiveByAdvisorID(Integer advisorID) {
        return menuRepository.findByAdvisor_AdvisorIDAndIsActiveTrue(advisorID);

    }

}
