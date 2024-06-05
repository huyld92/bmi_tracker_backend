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
    private Integer ingredientID;

    @Column(name = "IngredientName")
    private String ingredientName;

    @Column(name = "IngredientPhoto")
    private String ingredientPhoto;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "UnitOfMeasurement")
    private String unitOfMeasurement;

    @Column(name = "IngredientCalories")
    private Integer ingredientCalories;

    @Column(name = "TagID")
    private Integer tagID;

    @Column(name = "IsActive")
    private Boolean isActive;

    public Ingredient(Integer ingredientID) {
        this.ingredientID = ingredientID;
    }

    public Ingredient(String ingredientName, String ingredientPhoto, Integer quantity, String unitOfMeasurement, Integer ingredientCalories, Integer tagID, Boolean isActive) {
        this.ingredientName = ingredientName;
        this.ingredientPhoto = ingredientPhoto;
        this.quantity = quantity;
        this.unitOfMeasurement = unitOfMeasurement;
        this.ingredientCalories = ingredientCalories;
        this.tagID = tagID;
        this.isActive = isActive;
    }

    public Ingredient(CreateIngredientRequest ingredientRequest) {
        this.ingredientName = ingredientRequest.getIngredientName().trim();
        this.ingredientPhoto = ingredientRequest.getIngredientPhotoUrl().trim();
        this.unitOfMeasurement = ingredientRequest.getUnitOfMeasurement().trim();
        this.ingredientCalories = ingredientRequest.getIngredientCalories();
        this.tagID = ingredientRequest.getTagID();
        this.isActive = true;
    }

    public void update(Ingredient ingredientRequest) {
        if (!ingredientRequest.getIngredientName().isEmpty()) {
            this.ingredientName = ingredientRequest.getIngredientName();
        }

        if (!ingredientRequest.getUnitOfMeasurement().isEmpty()) {
            this.unitOfMeasurement = ingredientRequest.getUnitOfMeasurement();
        }
        this.ingredientPhoto = ingredientRequest.getIngredientPhoto();
        this.quantity = ingredientRequest.getQuantity();
        this.unitOfMeasurement = ingredientRequest.getUnitOfMeasurement();

        if (ingredientRequest.getIngredientCalories() > -1) {
            this.ingredientCalories = ingredientRequest.getIngredientCalories();
        }

        if (ingredientRequest.getTagID() <= 0) {
            this.tagID = ingredientRequest.getTagID();
        }
        this.isActive = ingredientRequest.isActive;

    }
}
