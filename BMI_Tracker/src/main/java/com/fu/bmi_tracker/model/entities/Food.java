/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.payload.request.CreateFoodRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
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
@Table(name = "Food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FoodID")
    private int foodID;

    @Column(name = "FoodName")
    private String foodName;

    @Column(name = "FoodCalories")
    private int foodCalories;

    @Column(name = "Description")
    private String description;

    @Column(name = "FoodPhoto")
    private String foodPhoto;

    @Column(name = "FoodVideo")
    private String foodVideo;

    @Column(name = "FoodNutrition")
    private String foodNutrition;

    @Column(name = "FoodTimeProcess")
    private int foodTimeProcess;

    @Column(name = "CreationDate")
    private LocalDate creationDate;

    @Column(name = "IsActive")
    private Boolean isActive;

    @OneToMany(mappedBy = "food")
    private List<Recipe> recipes;

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

    public void update(Food foodRequest) {
        if (foodRequest.getFoodName() != null) {
            this.foodName = foodRequest.getFoodName();
        }
        if (foodRequest.getFoodCalories() != 0) {
            this.foodCalories = foodRequest.getFoodCalories();
        }
        if (foodRequest.getDescription() != null) {
            this.description = foodRequest.getDescription();
        }
        if (foodRequest.getFoodPhoto() != null) {
            this.foodPhoto = foodRequest.getFoodPhoto();
        }
        if (foodRequest.getFoodVideo() != null) {
            this.foodVideo = foodRequest.getFoodVideo();
        }
        if (foodRequest.getFoodTimeProcess() != 0) {
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
