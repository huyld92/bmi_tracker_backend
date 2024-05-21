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
@Table(name = "Advisor")
public class Advisor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AdvisorID")
    private int advisorID;

    @Column(name = "AccountID")
    private int accountID;

    @Column(name = "Height")
    private float height;

    @Column(name = "Weight")
    private float weight;

    public Advisor(int accountID, float height, float weight) {
        this.accountID = accountID;
        this.height = height;
        this.weight = weight;
    }

    Advisor(int advisorID) {
        this.advisorID = advisorID;
    }

}
