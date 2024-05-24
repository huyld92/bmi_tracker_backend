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
@Table(name = "ActivityLevel")
public class ActivityLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ActivityLevelID")
    private Integer activityLevelID;

    @Column(name = "ActivityLevelName")
    private String activityLevelName;

    @Column(name = "ActivityLevel")
    private Double ActivityLevel;

    @Column(name = "IsActive")
    private boolean isActive;

    public ActivityLevel(Integer activityLevelID) {
        this.activityLevelID = activityLevelID;
    }
    
    

}
