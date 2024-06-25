/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Recipe;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Recipe> {

    public void deleteByFood_FoodIDAndIngredient_IngredientID(Integer foodID, Integer ingredientID);

    public Optional<Recipe> findByFood_FoodIDAndIngredient_IngredientID(Integer foodID, Integer ingredientID);

}
