/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.payload.request.CreateMenuRequest;
import com.fu.bmi_tracker.payload.request.UpdateMenuRequest;
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
@Table(name = "Menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MenuID", nullable = false)
    private Integer menuID;

    @Column(name = "MenuName", nullable = false)
    private String menuName;

    @Column(name = "MenuDescription", nullable = true)
    private String menuDescription;

    @Column(name = "TotalCalories", nullable = false)
    private Integer totalCalories;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    @Column(name = "AdvisorID", nullable = false)
    private Integer advisorID;

    public Menu(CreateMenuRequest menuRequest, Integer advisorID) {
        this.menuName = menuRequest.getMenuName();
        this.menuDescription = menuRequest.getMenuDescription();
        this.totalCalories = menuRequest.getTotalCalories();
        this.isActive = true;
        this.advisorID = advisorID;
    }

    public void update(UpdateMenuRequest menuRequest) {
        this.menuName = menuRequest.getMenuName();
        this.menuDescription = menuRequest.getMenuDescription();
        this.totalCalories = menuRequest.getTotalCalories();
    }
}
