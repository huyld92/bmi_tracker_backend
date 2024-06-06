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
import org.hibernate.annotations.SQLRestriction;

/**
 *
 * @author Duc Huy
 */
@Entity
@SQLRestriction(value = "IsActive = 1")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DietaryPreference")
public class DietaryPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DietaryPreferenceID", nullable = false)
    private Integer dietaryPreferenceID;

    @Column(name = "DietaryPreferenceName", nullable = false)
    private String dietaryPreferenceName;

    @Column(name = "Description", nullable = true)
    private String description;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    public DietaryPreference(Integer dietaryPreferenceID) {
        this.dietaryPreferenceID = dietaryPreferenceID;
    }
}
