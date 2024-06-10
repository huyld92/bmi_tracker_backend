/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Food;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

    Iterable<Food> findByFoodIDIn(List<Integer> foodIds);

    @Query("SELECT f FROM Food f JOIN TagFood ft ON f.foodID = ft.food.foodID JOIN Tag t ON ft.tag.tagID = t.tagID WHERE t.tagName = :dietPreferenceName")
    public Page<Food> findFoodsByTagName(String dietPreferenceName, Pageable pageable);

}
