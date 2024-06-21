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
    @Column(name = "AdvisorID", nullable = false)
    private Integer advisorID;

    @Column(name = "AccountID", nullable = false, unique = true)
    private Integer accountID;

    @Column(name = "Height", nullable = false)
    private Integer height;

    @Column(name = "Weight", nullable = false)
    private Integer weight;

    @Column(name = "TotalBooking", nullable = false)
    private Integer totalBooking;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    public Advisor(Integer accountID, Integer height, Integer weight, Integer totalBooking, boolean isActive) {
        this.accountID = accountID;
        this.height = height;
        this.weight = weight;
        this.totalBooking = totalBooking;
        this.isActive = isActive;
    }

    Advisor(Integer advisorID) {
        this.advisorID = advisorID;
    }

}
