/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Order;
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
    private String description;
    private Float amount;
    private LocalDateTime dateOrder;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer memberID;
    private Integer advisorID;
    private String status;
    private Integer transactionID;
    private Integer planDuration;
    private Boolean isPaid;

    public OrderResponse(Order order) {
        this.orderID = order.getOrderID();
        this.description = order.getDescription();
        this.amount = order.getOrderAmount();
        this.dateOrder = order.getDateOrder();
        this.startDate = order.getStartDate();
        this.endDate = order.getEndDate();
        this.memberID = order.getMember().getMemberID();
        this.advisorID = order.getAdvisor().getAdvisorID();
        this.status = order.getStatus().toString();
        this.transactionID = order.getTransactionID();
        this.isPaid = order.getIsPaid();
    }

}
