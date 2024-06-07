/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.payload.request.CreateFoodRequest;
import com.fu.bmi_tracker.payload.request.UpdateFoodRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

/**
 *
 * @author Duc Huy
 */
@Entity
@SQLRestriction(value = "IsActive = 1")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FoodID", nullable = false)
    private int foodID;

    @Column(name = "FoodName", nullable = false)
    private String foodName;

    @Column(name = "FoodCalories", nullable = false)
    private int foodCalories;

    @Column(name = "Description", nullable = true)
    private String description;

    @Column(name = "FoodPhoto", nullable = true)
    private String foodPhoto;

    @Column(name = "FoodVideo", nullable = true)
    private String foodVideo;

    @Column(name = "FoodNutrition", nullable = false)
    private String foodNutrition;

    @Column(name = "FoodTimeProcess", nullable = false)
    private int foodTimeProcess;

    @Column(name = "CreationDate", nullable = false)
    private LocalDate creationDate;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    @ManyToMany
    @JoinTable(
            name = "Recipe",
            joinColumns = @JoinColumn(name = "FoodID"),
            inverseJoinColumns = @JoinColumn(name = "IngredientID")
    )
    private List<Ingredient> ingredients;

    @ManyToMany
    @JoinTable(
            name = "FoodTag",
            joinColumns = @JoinColumn(name = "FoodID"),
            inverseJoinColumns = @JoinColumn(name = "TagID")
    )
    private List<Tag> foodTags;

    public Food(int foodID) {
        this.foodID = foodID;
    }

    public Food(CreateFoodRequest createFoodRequest) {
        this.foodName = createFoodRequest.getFoodName();
        this.foodCalories = createFoodRequest.getFoodCalories();
        this.description = createFoodRequest.getDescription();
        this.foodPhoto = createFoodRequest.getFoodPhoto();
        this.foodVideo = createFoodRequest.getFoodVideo();
        this.foodNutrition = createFoodRequest.getFoodNutrition();
        this.foodTimeProcess = createFoodRequest.getFoodTimeProcess();
        this.creationDate = LocalDate.now(ZoneId.of("GMT+7"));
        this.isActive = true;
    }

    public void update(UpdateFoodRequest foodRequest) {
        if (foodRequest.getFoodName() != null) {
            this.foodName = foodRequest.getFoodName();
        }
        if (foodRequest.getFoodCalories() != 0) {
            this.foodCalories = foodRequest.getFoodCalories();
        }
        this.description = foodRequest.getDescription();
        this.foodPhoto = foodRequest.getFoodPhoto();
        this.foodVideo = foodRequest.getFoodVideo();
        if (foodRequest.getFoodTimeProcess() >= 0) {
            this.foodTimeProcess = foodRequest.getFoodTimeProcess();
        }
        if (!foodRequest.getFoodNutrition().isEmpty()) {
            this.foodNutrition = foodRequest.getFoodNutrition();
        }
        if (foodRequest.getCreationDate() != null) {
            this.creationDate = foodRequest.getCreationDate();
        }
        if (foodRequest.getIsActive() != null) {
            this.isActive = foodRequest.getIsActive();
        }
    }

}
