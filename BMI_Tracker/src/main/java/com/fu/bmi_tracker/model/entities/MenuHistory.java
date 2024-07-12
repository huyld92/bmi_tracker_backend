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
import java.time.LocalDate;
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
@Table(name = "MenuHistory")
public class MenuHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MenuHistoryID", nullable = false)
    private Integer menuHistoryID;

    @Column(name = "DateOfAssigned", nullable = false)
    private LocalDate dateOfAssigned;

    @Column(name = "MenuID", nullable = false)
    private Integer menuID;

    @Column(name = "MemberID", nullable = false)
    private Integer memberID;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;
}
