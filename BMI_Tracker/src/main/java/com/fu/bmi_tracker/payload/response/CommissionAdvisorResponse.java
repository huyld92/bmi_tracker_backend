/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Commission;
import com.fu.bmi_tracker.model.enums.EPaymentStatus;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommissionAdvisorResponse {

    private Integer commissionID;
    private BigDecimal commissionAmount;
    private Integer commissionRate;
    private LocalDateTime paidDate;
    private LocalDate expectedPaymentDate;
    private BigDecimal paidAmount;
    private EPaymentStatus paymentStatus;
    private String commissionDescription;
    private Integer advisorID;
    private String advisorName;

    public CommissionAdvisorResponse(Commission commission) {
        this.commissionID = commission.getCommissionID();
        this.commissionAmount = commission.getCommissionAmount();
        this.commissionRate = commission.getCommissionRate();
        this.paidDate = commission.getPaidDate();
        this.expectedPaymentDate = commission.getExpectedPaymentDate();
        this.paidAmount = commission.getPaidAmount();
        this.paymentStatus = commission.getPaymentStatus();
        this.commissionDescription = commission.getCommissionDescription();
        this.advisorID = commission.getAdvisor().getAdvisorID();
        this.advisorName = commission.getAdvisor().getAccount().getFullName();
    }

}
