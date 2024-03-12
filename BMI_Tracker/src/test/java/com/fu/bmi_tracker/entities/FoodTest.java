/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.entities;

import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.Trainer;
import com.fu.bmi_tracker.repository.FoodRepository;
import java.util.Optional;
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
