/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "Ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IngredientID")
    private int ingredientID;

    @Column(name = "IngredientName")
    private String ingredientName;

    @Column(name = "IngredientPhoto")
    private String ingredientPhoto;

    @Column(name = "UnitOfMeasurement")
    private String unitOfMeasurement;

    @Column(name = "IngredientCalories")
    private int ingredientCalories;

    @Column(name = "IngredientType")
    private String ingredientType;

    @Column(name = "Status")
    private String status;

    public Ingredient(String ingredientName, String ingredientPhoto, String unitOfMeasurement, int ingredientCalories, String ingredientType, String status) {
        this.ingredientName = ingredientName;
        this.ingredientPhoto = ingredientPhoto;
        this.unitOfMeasurement = unitOfMeasurement;
        this.ingredientCalories = ingredientCalories;
        this.ingredientType = ingredientType;
        this.status = status;
    }
    
    
}
