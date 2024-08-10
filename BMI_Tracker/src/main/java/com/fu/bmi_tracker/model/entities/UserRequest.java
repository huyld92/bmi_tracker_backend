/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.model.entities.Account;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Duc Huy
 */
@Entity
@Data
@Table(name = "UserRequest")
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserRequestID")
    private int userRequestID;

    @Column(name = "Type", nullable = false, length = 50)
    private String type;

    @Column(name = "Purpose", nullable = false, length = 255)
    private String purpose;

    @Column(name = "ProcessNote", nullable = false, length = 255)
    private String processNote;

    @Column(name = "Status", nullable = false, length = 50)
    private String status;

    @Column(name = "CreationDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate creationDate;

    @Column(name = "ProcessingDate")
    @Temporal(TemporalType.DATE)
    private LocalDate processingDate;

    @ManyToOne
    @JoinColumn(name = "AccountID", nullable = false)
    private Account account;
}
