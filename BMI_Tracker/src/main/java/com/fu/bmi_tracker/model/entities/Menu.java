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

/**
 *
 * @author Duc Huy
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MenuID")
    private Integer menuID;

    @Column(name = "MenuName")
    private String menuName;

    @Column(name = "MenuDescription")
    private String menuDescription;

    @Column(name = "TotalCalories")
    private Integer totalCalories;

    @Column(name = "IsActive")
    private Boolean isActive;

    @Column(name = "AdvisorID")
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
