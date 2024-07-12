/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.AdvisorSubscription;
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
public class SubscriptionResponse {

    private Integer subscriptionID;
    private String subscriptionNumber;
    private String subscriptionDescription;
    private BigDecimal amount;
    private LocalDateTime subscriptionDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer memberID;
    private String memberName;
    private Integer advisorID;
    private String advisorName;
    private String subscriptionStatus;

    public SubscriptionResponse(AdvisorSubscription subscription) {
        this.subscriptionID = subscription.getSubscriptionID();
        this.subscriptionNumber = subscription.getSubscriptionNumber();
        this.subscriptionDescription = subscription.getSubscriptionDescription();
        this.amount = subscription.getSubscriptionAmount();
        this.subscriptionDate = subscription.getSubscriptionDate();
        this.startDate = subscription.getStartDate();
        this.endDate = subscription.getEndDate();
        this.memberID = subscription.getMember().getMemberID();
        this.memberName = subscription.getMember().getAccount().getFullName();
        this.advisorID = subscription.getAdvisor().getAdvisorID();
        this.advisorName = subscription.getAdvisor().getAccount().getFullName();
        this.subscriptionStatus = subscription.getSubscriptionStatus().toString();
    }

}
