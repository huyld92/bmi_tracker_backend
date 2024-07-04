/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.model.enums.EPaymentStatus;
import com.fu.bmi_tracker.payload.request.UpdateCommissionRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "Commission")
public class Commission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommissionID", nullable = false)
    private Integer commissionID;

    @Column(name = "CommissionAmount", nullable = false)
    private BigDecimal commissionAmount;

    @Column(name = "CommissionRate", nullable = false)
    private Integer commissionRate;

    @Column(name = "ExpectedPaymentDate", nullable = false)
    private LocalDate expectedPaymentDate;

    @Column(name = "PaidDate", nullable = true)
    private LocalDateTime paidDate;

    @Column(name = "PaidAmount", nullable = false)
    private BigDecimal paidAmount;

    @Column(name = "PaymentStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private EPaymentStatus paymentStatus;

    @Column(name = "CommissionDescription", nullable = true)
    private String commissionDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AdvisorID", nullable = false)
    private Advisor advisor;

    public Commission(BigDecimal commissionAmount, int commissionRate, LocalDateTime paidDate, LocalDate expectedPaymentDate, BigDecimal paidAmount, EPaymentStatus paymentStatus, String commissionDescription, Advisor advisor) {
        this.commissionAmount = commissionAmount;
        this.commissionRate = commissionRate;
        this.paidDate = paidDate;
        this.expectedPaymentDate = expectedPaymentDate;
        this.paidAmount = paidAmount;
        this.paymentStatus = paymentStatus;
        this.commissionDescription = commissionDescription;
        this.advisor = advisor;
    }

    public void update(UpdateCommissionRequest commissionRequest) {
        this.paidDate = commissionRequest.getPaidDate();
        this.paidAmount = commissionRequest.getPaidAmount();
        this.paymentStatus = commissionRequest.getPaymentStatus();
        this.commissionDescription = commissionRequest.getCommissionDescription();
    }

}
