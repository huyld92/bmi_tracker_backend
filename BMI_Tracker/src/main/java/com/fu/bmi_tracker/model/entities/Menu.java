/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.payload.request.CreateMenuRequest;
import com.fu.bmi_tracker.payload.request.UpdateMenuRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
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
    @Column(name = "MenuID", nullable = false)
    private Integer menuID;

    @Column(name = "MenuName", nullable = false)
    private String menuName;

    @Column(name = "MenuDescription", nullable = true)
    private String menuDescription;

    @Column(name = "TotalCalories", nullable = false)
    private Integer totalCalories;

    @Column(name = "CreationDate", nullable = false)
    private LocalDate creationDate;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "AdvisorID", nullable = false)
    private Advisor advisor;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MenuFood> menuFoods;

    public Menu(CreateMenuRequest menuRequest, Advisor advisor) {
        this.menuName = menuRequest.getMenuName();
        this.menuDescription = menuRequest.getMenuDescription();
        this.totalCalories = 0;
        this.creationDate = LocalDate.now();
        this.isActive = true;
        this.advisor = advisor;
    }

    public void update(UpdateMenuRequest menuRequest) {
        this.menuName = menuRequest.getMenuName();
//        this.menuPhoto = menuRequest.getMenuPhoto();
        this.menuDescription = menuRequest.getMenuDescription();
    }
}
