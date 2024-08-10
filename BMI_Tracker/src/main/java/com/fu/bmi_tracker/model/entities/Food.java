/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.payload.request.CreateFoodRequest;
import com.fu.bmi_tracker.payload.request.UpdateFoodRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    @Column(name = "FoodID", nullable = false)
    private int foodID;

    @Column(name = "FoodName", nullable = false,unique = true)
    private String foodName;

    @Column(name = "FoodCalories", nullable = false)
    private int foodCalories;

    @Column(name = "Description", nullable = true)
    private String description;

    @Column(name = "FoodPhoto", nullable = true)
    private String foodPhoto;

    @Column(name = "FoodVideo", nullable = true)
    private String foodVideo;

    @Column(name = "Serving", nullable = true)
    private Integer serving;

    @Column(name = "Carbs", nullable = true)
    private Float carbs;

    @Column(name = "Protein", nullable = true)
    private Float protein;

    @Column(name = "Fat", nullable = true)
    private Float fat;

    @Column(name = "FoodTimeProcess", nullable = false)
    private int foodTimeProcess;

    @Column(name = "CreationDate", nullable = false)
    private LocalDate creationDate;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FoodDetails> foodDetails;

    @ManyToMany
    @JoinTable(
            name = "TagFood",
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
        this.carbs = createFoodRequest.getCarbs();
        this.protein = createFoodRequest.getProtein();
        this.fat = createFoodRequest.getFat();
        this.serving = createFoodRequest.getServing();
        this.foodTimeProcess = createFoodRequest.getFoodTimeProcess();
        this.creationDate = LocalDate.now(ZoneId.of("GMT+7"));
        this.isActive = true;
    }

    public void update(UpdateFoodRequest foodRequest, List<Tag> foodTags) {
        if (foodRequest.getFoodName() != null) {
            this.foodName = foodRequest.getFoodName();
        }
        if (foodRequest.getFoodCalories() != 0) {
            this.foodCalories = foodRequest.getFoodCalories();
        }
        this.description = foodRequest.getDescription();
        this.serving = foodRequest.getServing();
        this.foodPhoto = foodRequest.getFoodPhoto();
        this.foodVideo = foodRequest.getFoodVideo();

        if (foodRequest.getFoodTimeProcess() >= 0) {
            this.foodTimeProcess = foodRequest.getFoodTimeProcess();
        }

        if (foodRequest.getCarbs() > 0) {
            this.carbs = foodRequest.getCarbs();
        }

        if (foodRequest.getProtein() > 0) {
            this.protein = foodRequest.getProtein();
        }

        if (foodRequest.getFat() > 0) {
            this.fat = foodRequest.getFat();
        }

        this.foodTags = foodTags;
    }

}
