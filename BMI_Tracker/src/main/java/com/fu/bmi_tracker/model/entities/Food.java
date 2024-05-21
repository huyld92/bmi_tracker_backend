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

    @Column(name = "FoodTimeProcess")
    private int foodTimeProcess;

    @Column(name = "IsActive")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "AdvisorID")
    private Advisor advisor;

    public Food(String foodName, int foodCalories, String description, String foodPhoto, String foodVideo, int foodTimeProcess, Boolean isActive, Advisor advisor) {
        this.foodName = foodName;
        this.foodCalories = foodCalories;
        this.description = description;
        this.foodPhoto = foodPhoto;
        this.foodVideo = foodVideo;
        this.foodTimeProcess = foodTimeProcess;
        this.isActive = isActive;
        this.advisor = advisor;
    }

    public Food(int foodID) {
        this.foodID = foodID;
    }

    public Food(CreateFoodRequest createFoodRequest) {
        this.foodName = createFoodRequest.getFoodName();
        this.foodCalories = createFoodRequest.getFoodCalories();
        this.description = createFoodRequest.getDescription();
        this.foodPhoto = createFoodRequest.getFoodPhoto();
        this.foodVideo = createFoodRequest.getFoodVideo();
        this.foodTimeProcess = createFoodRequest.getFoodTimeProcess();
        this.isActive = true;
    }

    public void update(Food foodRequest) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
