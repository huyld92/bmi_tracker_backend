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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Date;
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
    private Date dateOfMeal;

    @Column(name = "MealType")
    private String mealType;

    @Column(name = "Quantity")
    private String quantity;

    @Column(name = "Status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "FoodID")
    private Food food;
}
