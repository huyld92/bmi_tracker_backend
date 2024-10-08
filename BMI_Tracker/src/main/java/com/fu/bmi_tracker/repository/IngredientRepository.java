/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Ingredient;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    public List<Ingredient> findByIngredientIDIn(List<Integer> ingredientIds);

    public Iterable<Ingredient> findByIngredientNameContains(String ingredientName);

    public Optional<Ingredient> findByIngredientIDAndIsActiveTrue(Integer ingredientID);

    public Iterable<Ingredient> findByIsActiveTrue();

}
