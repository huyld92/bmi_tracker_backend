/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.entities;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.FoodTag;
import com.fu.bmi_tracker.model.entities.Tag;
import com.fu.bmi_tracker.model.entities.Trainer;
import com.fu.bmi_tracker.repository.FoodRepository;
import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 *
 * @author Duc Huy
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FoodTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private FoodRepository repository;
    
    @Test
    public void should_find_no_foods_if_repository_is_empty() {
        Iterable<Food> foods = repository.findAll();
        
        assertThat(foods).isEmpty();
    }
    
    @Test
    public void should_store_a_food() {
        Trainer trainer = new Trainer();
        trainer.setTrainerID(6);
        Food food = new Food("Egg bold", 50,
                "Healthy food", "egg.jpg", "egg.mp4",
                5, "Active", trainer);
        
        Food foodResult = repository.save(food);
        
        assertThat(foodResult)
                .hasFieldOrPropertyWithValue("foodName", "Egg bold")
                .hasFieldOrPropertyWithValue("foodCalories", 50)
                .hasFieldOrPropertyWithValue("description", "Healthy food")
                .hasFieldOrPropertyWithValue("foodPhoto", "egg.jpg")
                .hasFieldOrPropertyWithValue("foodVideo", "egg.mp4")
                .hasFieldOrPropertyWithValue("foodTimeProcess", 5)
                .hasFieldOrPropertyWithValue("status", "Active")
                .hasFieldOrPropertyWithValue("trainer", trainer);
    }
    
    @Test
    public void should_store_a_food_with_foodTags() {
        Trainer trainer = new Trainer();
        trainer.setTrainerID(6);
//        Food food = new Food("Egg bold", 50,
//                "Healthy food", "egg.jpg", "egg.mp4",
//                5, "Active", trainer);

        // Given
        Tag tag1 = new Tag();
        tag1.setTagName("Healthy");
        tag1.setStatus("Active");
        
        Tag tag2 = new Tag();
        tag2.setTagName("Vegan");
        tag2.setStatus("Active");
        
        Food food = new Food();
        food.setFoodName("Salad");
        food.setFoodCalories(200);
        food.setDescription("Fresh and healthy salad");
        food.setFoodPhoto("salad.jpg");
        food.setFoodVideo("salad.mp4");
        food.setFoodTimeProcess(10);
        food.setStatus("Available");
        food.setFoodTags(new ArrayList<>());
        
        FoodTag foodTag1 = new FoodTag();
        foodTag1.setTag(tag1);
        foodTag1.setFood(food);
        
        FoodTag foodTag2 = new FoodTag();
        foodTag2.setTag(tag2);
        foodTag2.setFood(food);
        
        food.getFoodTags().add(foodTag1);
        food.getFoodTags().add(foodTag2);

        // When
        Food savedFood = repository.save(food);

        // Then
        assertThat(savedFood).isNotNull();
        assertThat(savedFood.getFoodID()).isGreaterThan(0);
        assertThat(savedFood.getFoodTags()).isNotEmpty();
        assertThat(savedFood.getFoodTags()).hasSize(2);
    }
    
    @Test
    public void should_find_all_foods() {
        // Given
        Trainer trainer = new Trainer();
        trainer.setTrainerID(6);
        Food food1 = new Food("Apple", 50, "Healthy fruit",
                "apple.jpg", "apple.mp4", 5, "Active", trainer);
        Food food2 = new Food("Banana", 80, "Another healthy fruit",
                "banana.jpg", "banana.mp4", 3, "Active", trainer);
        entityManager.persist(food1);
        entityManager.persist(food2);

        // When
        Iterable<Food> foundFoods = repository.findAll();

        // Then
        assertThat(foundFoods).hasSize(2);
        assertThat(foundFoods).contains(food1, food2);
    }

//    @Test
//    public void should_find_food_by_id() {
//        // Given
//        Food food = new Food("Apple", 50, "Healthy fruit",
//                "apple.jpg", "apple.mp4", 5, "Active", 123);
//
//        // When
//        Optional<Food> foundFood = repository.findById(1);
//
//        // Then
//        assertThat(foundFood).isPresent();
//        assertThat(foundFood.get()).isEqualTo(food);
//    }
}
