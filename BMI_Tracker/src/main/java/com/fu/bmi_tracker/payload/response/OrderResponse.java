/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Order;
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
public class OrderResponse {

    private Integer orderID;
    private String oderNumber;
    private String oderDescription;
    private BigDecimal amount;
    private LocalDateTime orderDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer memberID;
    private Integer advisorID;
    private String oderOtatus;

    public OrderResponse(Order order) {
        this.orderID = order.getOrderID();
        this.oderNumber = order.getOrderNumber();
        this.oderDescription = order.getOrderDescription();
        this.amount = order.getOrderAmount();
        this.orderDate = order.getOrderDate();
        this.startDate = order.getStartDate();
        this.endDate = order.getEndDate();
        this.memberID = order.getMember().getMemberID();
        this.advisorID = order.getAdvisor().getAdvisorID();
        this.oderOtatus = order.getOrderStatus().toString();
    }

}
