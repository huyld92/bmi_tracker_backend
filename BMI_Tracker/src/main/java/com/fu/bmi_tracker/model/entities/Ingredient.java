/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.payload.request.CreateIngredientRequest;
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
        this.ingredientName = ingredientName.trim();
        this.ingredientPhoto = ingredientPhoto.trim();
        this.unitOfMeasurement = unitOfMeasurement.trim();
        this.ingredientCalories = ingredientCalories;
        this.ingredientType = ingredientType.trim();
        this.status = status;
    }

    public Ingredient(CreateIngredientRequest ingredientRequest) {
        this.ingredientName = ingredientRequest.getIngredientName().trim();
        this.unitOfMeasurement = ingredientRequest.getUnitOfMeasurement().trim();
        this.ingredientCalories = ingredientRequest.getIngredientCalories();
        this.ingredientType = ingredientRequest.getIngredientType().trim();
        this.status = "Active";
    }

    public void update(Ingredient ingredientRequest) {
        if (!ingredientRequest.getIngredientName().isEmpty()) {
            this.ingredientName = ingredientRequest.getIngredientName();
        }

        if (!ingredientRequest.getUnitOfMeasurement().isEmpty()) {
            this.unitOfMeasurement = ingredientRequest.getUnitOfMeasurement();
        }

        if (ingredientRequest.getIngredientCalories() > -1) {
            this.ingredientCalories = ingredientRequest.getIngredientCalories();
        }

        if (!ingredientRequest.getIngredientType().isEmpty()) {
            this.ingredientType = ingredientRequest.getIngredientType();
        }

    }
}
