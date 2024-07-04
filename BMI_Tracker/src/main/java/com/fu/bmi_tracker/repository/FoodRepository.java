/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Food;
import java.util.List;
import java.util.Optional;
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

    @Query("SELECT f FROM Food f "
            + "JOIN TagFood tf ON f.foodID = tf.food.foodID "
            + "JOIN Tag t ON tf.tag.tagID = t.tagID"
            + " WHERE t.tagName = :dietPreferenceName AND f.isActive = true")
    public Page<Food> findFoodWithTagName(String dietPreferenceName, Pageable pageable);

    @Query("SELECT f FROM Food f WHERE f.foodID IN "
            + "(SELECT tf.food.foodID FROM TagFood tf JOIN Tag t ON tf.tag.tagID = t.tagID "
            + "WHERE t.tagName = :tagName) AND f.isActive = true")
    public List<Food> findFoodWithTagName(String tagName);

    @Query("SELECT f FROM Food f WHERE f.foodID NOT IN "
            + "(SELECT tf.food.foodID FROM TagFood tf JOIN Tag t ON tf.tag.tagID = t.tagID "
            + "WHERE t.tagName = :tagName) AND f.isActive = true")
    List<Food> findFoodWithoutTagName(String tagName);

    public Iterable<Food> findByFoodNameContains(String foodName);

    public Optional<Food> findByFoodIDAndIsActiveTrue(Integer foodID);
}
