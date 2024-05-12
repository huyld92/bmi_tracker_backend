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
import java.time.Instant;
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
@Table(name = "UserBodyMass")
public class UserBodyMass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserBodyMassID")
    private int userBodyMassID;

    @Column(name = "Height")
    private int height;

    @Column(name = "Weight")
    private int weight;

    @Column(name = "Age")
    private int age;

    @Column(name = "BMI")
    private double bmi;

    @Column(name = "DateInput")
    private Instant dateInput;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    public UserBodyMass(int height, int weight, int age, double bmi, Instant dateInput, User user) {
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.bmi = bmi;
        this.dateInput = dateInput;
        this.user = user;
    }

}
