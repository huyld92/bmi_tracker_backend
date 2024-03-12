/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.entities;

import static org.assertj.core.api.Assertions.assertThat;
import com.fu.bmi_tracker.model.entities.Ingredient;
import com.fu.bmi_tracker.repository.IngredientRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class IngredientTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IngredientRepository repository;

    @Test
    public void should_store_a_ingredient() {
        // Create a new Ingredient object
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientName("TestIngredient");
        ingredient.setIngredientPhoto("test.jpg");
        ingredient.setUnitOfMeasurement("grams");
        ingredient.setIngredientCalories(100);
        ingredient.setIngredientType("TestType");
        ingredient.setStatus("Active");

        // Save the ingredient to the database
        Ingredient savedIngredient = repository.save(ingredient);

        // Retrieve the ingredient from the database
        Ingredient retrievedIngredient = repository.findById(savedIngredient.getIngredientID()).orElse(null);

        // Assert that the retrieved ingredient matches the saved ingredient
        assertEquals("TestIngredient", retrievedIngredient.getIngredientName());
        assertEquals("test.jpg", retrievedIngredient.getIngredientPhoto());
        assertEquals("grams", retrievedIngredient.getUnitOfMeasurement());
        assertEquals(100, retrievedIngredient.getIngredientCalories());
        assertEquals("TestType", retrievedIngredient.getIngredientType());
        assertEquals("Active", retrievedIngredient.getStatus());
    }

    @Test
    public void should_find_all_ingredients() {
        // Save some sample ingredients to the database
        Ingredient ingredient1 = new Ingredient("Ingredient1", "photo1.jpg", "grams", 100, "Type1", "Active");
        Ingredient ingredient2 = new Ingredient("Ingredient2", "photo2.jpg", "grams", 200, "Type2", "Active");

        entityManager.persist(ingredient1);
        entityManager.persist(ingredient2);

        // Retrieve all ingredients from the database
        Iterable<Ingredient> allIngredients = repository.findAll();
        System.out.println(allIngredients.toString());
        // Assert using AssertJ
        assertThat(allIngredients).hasSize(3).contains(ingredient1, ingredient2);
    }

//    @Test
//    public void should_find_ingredient_by_id() {
//        Ingredient ingredient1 = new Ingredient("Ingredient1", "photo1.jpg", "grams", 100, "Type1", "Active");
//        entityManager.persist(ingredient1);
//
//        Ingredient ingredient2 = new Ingredient("Ingredient2", "photo2.jpg", "grams", 200, "Type2", "Active");
//        entityManager.persist(ingredient2);
//
//        Ingredient foundIngredient = repository.findById(ingredient2.getIngredientID()).get();
//
//        assertThat(foundIngredient).isEqualTo(ingredient2);
//    }
}
