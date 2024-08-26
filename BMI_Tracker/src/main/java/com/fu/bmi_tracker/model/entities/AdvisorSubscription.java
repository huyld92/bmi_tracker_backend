/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.model.enums.ESubscriptionStatus;
import com.fu.bmi_tracker.payload.request.SubscriptionRequest;
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
import java.time.ZoneId;
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
@Table(name = "[AdvisorSubscription]")
public class AdvisorSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SubscriptionID", nullable = false)
    private Integer subscriptionID;

    @Column(name = "SubscriptionDescription", nullable = false)
    private String subscriptionDescription;

    @Column(name = "SubscriptionNumber", nullable = false)
    private String subscriptionNumber;

    @Column(name = "SubscriptionAmount", nullable = false)
    private BigDecimal subscriptionAmount;

    @Column(name = "SubscriptionDate", nullable = false)
    private LocalDateTime subscriptionDate;

    @Column(name = "StartDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "EndDate", nullable = false)
    private LocalDate endDate;

    @Column(name = "SubscriptionStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private ESubscriptionStatus subscriptionStatus;

    @Column(name = "CommissionRate", nullable = false)
    private Float commissionRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MemberID", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AdvisorID", nullable = false)
    private Advisor advisor;

    @Column(name = "TransactionID", nullable = false, unique = true)
    private Integer transactionID;

    public AdvisorSubscription(SubscriptionRequest subscriptionRequest,
            LocalDate startDate,
            LocalDate endDate,
            LocalDateTime subscriptionDate,
            Member member,
            Advisor advisor,
            int transactionID,
            Float commissionRate) {
        this.subscriptionDescription = subscriptionRequest.getDescription();
        this.subscriptionNumber = subscriptionRequest.getSubscriptionNumber();
        this.subscriptionAmount = subscriptionRequest.getAmount();
        this.subscriptionDate = subscriptionDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.member = member;
        this.commissionRate = commissionRate;
        this.advisor = advisor;
        this.subscriptionStatus = checkSubscriptionStatus(startDate);
        this.transactionID = transactionID;
    }

    private ESubscriptionStatus checkSubscriptionStatus(LocalDate startDate) {
        LocalDate now = LocalDate.now();
        if (now.isBefore(startDate)) {
            //ngày hiện tại trước startDate
            return ESubscriptionStatus.NOT_STARTED;
        } else if (now.equals(startDate)) {
            //ngày hiện tại bằng  startDate
            return ESubscriptionStatus.PENDING;
        } else {
            return ESubscriptionStatus.FINISHED;
        }
    }
}
