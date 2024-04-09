/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Ingredient;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface IngredientService extends GeneralService<Ingredient> {

    public List<Ingredient> findByIngredientIDIn(List<Integer> ingredientIds);

}
