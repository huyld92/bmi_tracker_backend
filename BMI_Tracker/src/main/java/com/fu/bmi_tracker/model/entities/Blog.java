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
@Table(name = "Blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BlogID")
    private Integer blogID;

    @Column(name = "BlogName")
    private String blogName;

    @Column(name = "BlogContent")
    private String blogContent;

    @Column(name = "BlogPhoto")
    private String blogPhoto;

    @Column(name = "Link")
    private String link;

    @Column(name = "IsActive")
    private boolean isActive;

    @Column(name = "AdvisorID")
    private Integer advisorID;
}
