/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.model.enums.EOrderStatus;
import com.fu.bmi_tracker.payload.request.CreateOderRequest;
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

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "OrderAmount", nullable = false)
    private Float orderAmount;

    @Column(name = "DateOrder", nullable = false)
    private LocalDateTime dateOrder;

    @Column(name = "StartDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "EndDate", nullable = false)
    private LocalDate endDate;

    @Column(name = "Status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EOrderStatus status;

    @Column(name = "CommissionRate", nullable = false)
    private Float commissionRate;

    @Column(name = "CommissionAmount", nullable = false)
    private Float commissionAmount;

    @Column(name = "IsPaid", nullable = false)
    private Boolean isPaid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MemberID", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AdvisorID", nullable = false)
    private Advisor advisor;

    @Column(name = "TransactionID", nullable = false, unique = true)
    private Integer transactionID;

    public Order(CreateOderRequest createOderRequest, Integer memberID) {
        this.description = createOderRequest.getDescription();
        this.orderAmount = createOderRequest.getAmount();
        this.dateOrder = LocalDateTime.now(ZoneId.of("GMT+7"));
        this.startDate = LocalDate.now(ZoneId.of("GMT+7"));
        this.endDate = startDate.plusDays(createOderRequest.getPlanDuration());
        this.member = new Member();
        this.member.setMemberID(memberID);
        this.advisor = new Advisor();
        this.advisor.setAdvisorID(createOderRequest.getAdvisorID());
        this.status = EOrderStatus.PENDING;
        this.isPaid = false;
    }

}
