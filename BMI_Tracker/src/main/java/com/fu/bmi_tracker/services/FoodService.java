/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.payload.request.CreateFoodRequest;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Duc Huy
 */
public interface FoodService extends GeneralService<Food> {

    public Iterable<Food> findByFoodIDIn(List<Integer> foodIDs);

    public Page<Food> getFoodsByTagName(String dietPreferenceName, Pageable pageable);

    public Food createNewFood(CreateFoodRequest createFoodRequest);

}
