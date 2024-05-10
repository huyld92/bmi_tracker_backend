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
@Table(name = "CustomerBodyMass")
public class CustomerBodyMass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerBodyMassID")
    private int customerBodyMassID;

    @Column(name = "Height")
    private float height;

    @Column(name = "Weight")
    private float weight;

    @Column(name = "Age")
    private float age;

    @Column(name = "BMI")
    private float bmi;

    @Column(name = "DateInput")
    private Instant dateInput;

    @Column(name = "Status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private User customer;
}
