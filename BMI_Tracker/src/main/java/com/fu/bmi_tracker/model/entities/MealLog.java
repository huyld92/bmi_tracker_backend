/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.model.enums.EMealType;
import com.fu.bmi_tracker.payload.request.CreateMealLogRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
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
@Table(name = "MealLog")
public class MealLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MealLogID")
    private int mealLogID;

    @Column(name = "FoodName")
    private String foodName;

    @Column(name = "Calories")
    private int calories;

    @Column(name = "DateOfMeal")
    private LocalDate dateOfMeal;

    @Column(name = "MealType")
    @Enumerated(EnumType.STRING)
    private EMealType mealType;

    @Column(name = "Quantity")
    private String quantity;

    @Column(name = "Status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "MemberID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "FoodID", nullable = true)
    private Food food;

    public MealLog(CreateMealLogRequest createMealLogRequest) {
        this.foodName = createMealLogRequest.getFoodName();
        this.calories = createMealLogRequest.getCalories();
        this.dateOfMeal = createMealLogRequest.getDateOfMeal();
        this.mealType = createMealLogRequest.getMealType();
        this.quantity = createMealLogRequest.getQuantity();
        this.member = new Member(createMealLogRequest.getMemberID());
        this.food = new Food(createMealLogRequest.getFoodID());
        this.status = "New";
    }

}
