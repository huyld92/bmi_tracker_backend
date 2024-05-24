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
    private Integer orderID;

    @Column(name = "Description")
    private String description;

    @Column(name = "Amount")
    private Float amount;

    @Column(name = "DateOrder")
    private LocalDateTime dateOrder;

    @Column(name = "memberID", nullable = false)
    private Integer memberID;

    @Column(name = "AdvisorID", nullable = false)
    private Integer advisorID;

    @Column(name = "Status")
    private String status;

    @Column(name = "TransactionID", nullable = false)
    private Integer transactionID;

    @Column(name = "PlanID", nullable = false)
    private Integer planID;

}
