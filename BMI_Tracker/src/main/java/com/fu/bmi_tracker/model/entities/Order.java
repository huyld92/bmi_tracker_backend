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
import java.time.LocalDateTime;
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
@Table(name = "[Order]")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID")
    private int orderID;

    @Column(name = "Description")
    private String description;

    @Column(name = "Status")
    private String status;

    @Column(name = "Amount")
    private float amount;

    @Column(name = "DateOrder")
    private LocalDateTime dateOrder;

    @Column(name = "UserID", nullable = false)
    private int userID;

    @Column(name = "TrainerServiceID", nullable = false)
    private int trainerServiceID;

    @Column(name = "TransactionID", nullable = false)
    private int transactionID;
}
