/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Duc Huy
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(Recipe.class)
@Table(name = "Recipe")
public class Recipe {

    @Id
    @ManyToOne
    @JoinColumn(name = "FoodID")
    @JsonIgnore
    private Food food;

    @Id
    @ManyToOne
    @JoinColumn(name = "IngredientID")
    private Ingredient ingredient;

    @Column(name = "quantity")
    private Integer quantity;
}
