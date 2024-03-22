/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.entities;

import com.fu.bmi_tracker.model.entities.Customer;
import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.MealLog;
import com.fu.bmi_tracker.model.entities.Trainer;
import com.fu.bmi_tracker.model.enums.EMealType;
import com.fu.bmi_tracker.repository.CustomerRepository;
import com.fu.bmi_tracker.repository.MealLogRepository;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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
public class MealLogTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MealLogRepository repository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void should_find_no_mealLogs_if_repository_is_empty() {
        Iterable<MealLog> mealLogs = repository.findAll();

        assertThat(mealLogs).isEmpty();
    }

    @Test
    public void should_store_a_mrealLog() {
        // Given
        MealLog mealLog = new MealLog();
        mealLog.setFoodName("Pizza");
        mealLog.setCalories(500);
        mealLog.setDateOfMeal(Date.valueOf("2024-03-11"));
        mealLog.setMealType(EMealType.Breakfast);
        mealLog.setQuantity("1 slice");
        mealLog.setStatus("Active");

        // When
        MealLog mealLogResult = repository.save(mealLog);

        // Then
        assertThat(mealLogResult).isNotNull();

        assertThat(mealLogResult)
                .hasFieldOrPropertyWithValue("foodName", "Pizza")
                .hasFieldOrPropertyWithValue("calories", 500)
                .hasFieldOrPropertyWithValue("mealType", EMealType.Breakfast)
                .hasFieldOrPropertyWithValue("quantity", "1 slice")
                .hasFieldOrPropertyWithValue("status", "Active");
    }

    @Test
    public void should_store_mealLogs() {
        // Given
        List<MealLog> mealLogsToSave = new ArrayList<>();

        MealLog mealLog1 = new MealLog();
        mealLog1.setFoodName("Pizza");
        mealLog1.setCalories(500);
        mealLog1.setDateOfMeal(Date.valueOf("2024-03-11"));
        mealLog1.setMealType(EMealType.Breakfast);
        mealLog1.setQuantity("1 slice");
        mealLog1.setStatus("Eaten");

        MealLog mealLog2 = new MealLog();
        mealLog2.setFoodName("Salad");
        mealLog2.setCalories(200);
        mealLog2.setDateOfMeal(Date.valueOf("2024-03-12"));
        mealLog2.setMealType(EMealType.Lunch);
        mealLog2.setQuantity("1 bowl");
        mealLog2.setStatus("Eaten");

        mealLogsToSave.add(mealLog1);
        mealLogsToSave.add(mealLog2);

        // When
        Iterable<MealLog> savedMealLogs = repository.saveAll(mealLogsToSave);

        // Then
        assertThat(savedMealLogs).isNotEmpty();
        assertThat(savedMealLogs).hasSize(2);
    }

    @Test
    public void should_find_all_mealLogs() {
        // Given
        MealLog mealLog1 = new MealLog();
        mealLog1.setFoodName("Pizza");
        mealLog1.setCalories(500);
        mealLog1.setDateOfMeal(Date.valueOf("2024-03-11"));
        mealLog1.setMealType(EMealType.Dinner);
        mealLog1.setQuantity("1 slice");
        mealLog1.setStatus("Eaten");
        repository.save(mealLog1);

        MealLog mealLog2 = new MealLog();
        mealLog2.setFoodName("Salad");
        mealLog2.setCalories(200);
        mealLog2.setDateOfMeal(Date.valueOf("2024-03-12"));
        mealLog2.setMealType(EMealType.Lunch);
        mealLog2.setQuantity("1 bowl");
        mealLog2.setStatus("Eaten");
        repository.save(mealLog2);

        // When
        Iterable<MealLog> mealLogs = repository.findAll();

        // Then
        assertThat(mealLogs).isNotEmpty();
        assertThat(mealLogs).hasSize(2);
    }
}
