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
@Table(name = "Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionID", nullable = false)
    private Integer transactionID;

    @Column(name = "BankCode", nullable = false)
    private String bankCode;

    @Column(name = "BankTranNo", nullable = false)
    private String bankTranNo;

    @Column(name = "CardType", nullable = false)
    private String cardType;

    @Column(name = "Amount", nullable = false)
    private Integer amount;

    @Column(name = "OrderInfo", nullable = false)
    private String orderInfo;

    @Column(name = "PayDate", nullable = false)
    private LocalDateTime payDate;

    @Column(name = "MemberID", nullable = false)
    private Integer memberID;

    public Transaction(String bankCode, String bankTranNo, String cardType, Integer amount, String orderInfo, LocalDateTime payDate, Integer memberID) {
        this.bankCode = bankCode;
        this.bankTranNo = bankTranNo;
        this.cardType = cardType;
        this.amount = amount;
        this.orderInfo = orderInfo;
        this.payDate = payDate;
        this.memberID = memberID;
    }

}
