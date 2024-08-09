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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AdvisorID", nullable = false)
    private Integer advisorID;

    @OneToOne()
    @JoinColumn(name = "AccountID", nullable = false, unique = true)
    private Account account;

    @Column(name = "BankNumber", nullable = false)
    private String bankNumber;

    @Column(name = "BankName", nullable = false)
    private String bankName;

    @Column(name = "totalSubscription", nullable = false)
    private Integer totalSubscription;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    public Advisor(Account account, String bankNumber, String bankName, Integer totalSubscription, Boolean isActive) {
        this.account = account;
        this.bankNumber = bankNumber;
        this.bankName = bankName;
        this.totalSubscription = totalSubscription;
        this.isActive = isActive;
    }

    Advisor(Integer advisorID) {
        this.advisorID = advisorID;
    }

}
