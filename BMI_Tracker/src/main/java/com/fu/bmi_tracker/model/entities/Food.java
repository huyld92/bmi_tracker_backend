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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @Column(name = "FoodTimeProcess")
    private int foodTimeProcess;

    @Column(name = "Status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "TrainerID")
    private Trainer trainer;

    public Food(String foodName, int foodCalories, String description, String foodPhoto, String foodVideo, int foodTimeProcess, String status, Trainer trainer) {
        this.foodName = foodName;
        this.foodCalories = foodCalories;
        this.description = description;
        this.foodPhoto = foodPhoto;
        this.foodVideo = foodVideo;
        this.foodTimeProcess = foodTimeProcess;
        this.status = status;
        this.trainer = trainer;
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
        this.status = "Active";
    }

    public void update(Food foodRequest) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
