/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.model.enums.EOrderStatus;
import com.fu.bmi_tracker.payload.request.OrderRequest;
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
@Table(name = "[Order]")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID", nullable = false)
    private Integer orderID;

    @Column(name = "OrderDescription", nullable = false)
    private String orderDescription;

    @Column(name = "OrderNumber", nullable = false)
    private String orderNumber;

    @Column(name = "OrderAmount", nullable = false)
    private BigDecimal orderAmount;

    @Column(name = "OrderDate", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "StartDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "EndDate", nullable = false)
    private LocalDate endDate;

    @Column(name = "OrderStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private EOrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MemberID", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AdvisorID", nullable = false)
    private Advisor advisor;

    @Column(name = "CommissionID", nullable = false)
    private Integer commissionID;

    @Column(name = "TransactionID", nullable = false, unique = true)
    private Integer transactionID;

    public Order(OrderRequest orderRequest,
            LocalDate startDate,
            LocalDate endDate,
            Integer memberID,
            Advisor advisor,
            int transactionID,
            int commissionID) {
        this.orderDescription = orderRequest.getDescription();
        this.orderNumber = orderRequest.getOrderNumber();
        this.orderAmount = orderRequest.getAmount();
        this.orderDate = LocalDateTime.now(ZoneId.of("GMT+7"));
        this.startDate = startDate;
        this.endDate = endDate;
        this.member = new Member();
        this.member.setMemberID(memberID);
        this.advisor = advisor;
        this.orderStatus = EOrderStatus.MEMBER_PAID;
        this.commissionID = commissionID;
        this.transactionID = transactionID;
    }

}
