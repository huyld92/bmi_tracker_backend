/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@IdClass(CustomerLimitation.class)
@Table(name = "CustomerLimitation")
public class CustomerLimitation {

    @ManyToOne
    @Id
    @JoinColumn(name = "CustomerCustomerID")
    private Customer customer;

    @ManyToOne
    @Id
    @JoinColumn(name = "LimitationLimitationID")
    private Limitation limitation;

}
