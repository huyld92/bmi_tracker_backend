/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.payload.request.MemberTransactionRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
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
@Table(name = "MemberTransaction")
public class MemberTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionID")
    private int transactionID;

    @Column(name = "ZpTransToken", length = 20, nullable = false)
    private String zpTransToken;

    @Column(name = "PayDate", nullable = false)
    private LocalDateTime payDate;

    @Column(name = "TransactionMessage", length = 255, nullable = false)
    private String transactionMessage;

    @Column(name = "TransactionSubMessage", length = 255, nullable = false)
    private String transactionSubMessage;

    @Column(name = "Amount", precision = 18, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "OrderToken", length = 255, nullable = false)
    private String orderToken;

    @Column(name = "MemberID", nullable = false)
    private int memberID;

    public MemberTransaction(MemberTransactionRequest transactionRequest, Integer memberID) {
        this.zpTransToken = transactionRequest.getZpTransToken();
        this.payDate = transactionRequest.getPayDate();
        this.transactionMessage = transactionRequest.getTransactionMessage();
        this.transactionSubMessage = transactionRequest.getTransactionSubMessage();
        this.amount = transactionRequest.getAmount();
        this.orderToken = transactionRequest.getOrderToken();
        this.memberID = memberID;
    }

}
