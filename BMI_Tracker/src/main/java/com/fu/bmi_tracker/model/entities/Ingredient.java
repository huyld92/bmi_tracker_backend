/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.payload.request.CreateIngredientRequest;
import com.fu.bmi_tracker.payload.request.UpdateIngredientRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "Ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IngredientID", nullable = false)
    private Integer ingredientID;

    @Column(name = "IngredientName", nullable = false)
    private String ingredientName;

    @Column(name = "IngredientPhoto", nullable = true)
    private String ingredientPhoto;

    @Column(name = "NutritionalInformation", nullable = true)
    private String nutritionalInformation;

    @Column(name = "Quantity", nullable = false)
    private Float quantity;

    @Column(name = "Unit", nullable = false)
    private String unit;

    @Column(name = "IngredientCalories", nullable = false)
    private Integer ingredientCalories;

    @ManyToOne
    @JoinColumn(name = "TagID", nullable = false)
    private Tag tag;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    public Ingredient(Integer ingredientID) {
        this.ingredientID = ingredientID;
    }

    public Ingredient(CreateIngredientRequest ingredientRequest) {
        this.ingredientName = ingredientRequest.getIngredientName().trim();
        this.ingredientPhoto = ingredientRequest.getIngredientPhoto().trim();
        this.unit = ingredientRequest.getUnit().trim();
        this.ingredientCalories = ingredientRequest.getIngredientCalories();
        this.quantity = ingredientRequest.getQuantity();
        this.tag = new Tag(ingredientRequest.getTagID());
        this.isActive = true;
    }

    public void update(UpdateIngredientRequest ingredientRequest) {
        if (!ingredientRequest.getIngredientName().isEmpty()) {
            this.ingredientName = ingredientRequest.getIngredientName();
        }

        if (!ingredientRequest.getUnit().isEmpty()) {
            this.unit = ingredientRequest.getUnit();
        }
        this.ingredientPhoto = ingredientRequest.getIngredientPhoto();
        this.quantity = ingredientRequest.getQuantity();

        if (ingredientRequest.getIngredientCalories() > -1) {
            this.ingredientCalories = ingredientRequest.getIngredientCalories();
        }

        if (ingredientRequest.getTagID() <= 0) {
            this.tag = new Tag(ingredientRequest.getTagID());
        }

    }

    public Integer get() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
