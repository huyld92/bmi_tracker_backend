/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Duc Huy
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Advisor")
public class Advisor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AdvisorID", nullable = false)
    private Integer advisorID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AccountID", nullable = false, unique = true)
    private Account account;

//    @Column(name = "Height", nullable = false)
//    private Integer height;
//
//    @Column(name = "Weight", nullable = false)
//    private Integer weight;

    @Column(name = "totalSubscription", nullable = false)
    private Integer totalSubscription;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    public Advisor(Account account, Integer height, Integer weight, Integer totalSubscription, boolean isActive) {
        this.account = account;
        this.height = height;
        this.weight = weight;
        this.totalSubscription = totalSubscription;
        this.isActive = isActive;
    }

    Advisor(Integer advisorID) {
        this.advisorID = advisorID;
    }

}
