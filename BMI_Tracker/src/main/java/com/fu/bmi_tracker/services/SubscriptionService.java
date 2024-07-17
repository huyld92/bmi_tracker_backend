/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.AdvisorSubscription;
import com.fu.bmi_tracker.payload.request.CreateSubscriptionTransactionRequest;
import com.fu.bmi_tracker.payload.response.AdvisorSubscriptionSummary;
import com.fu.bmi_tracker.payload.response.AdvisorDetailsResponse;
import com.fu.bmi_tracker.payload.response.CountSubscriptionResponse;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface SubscriptionService extends GeneralService<AdvisorSubscription> {

    public Iterable<AdvisorSubscription> getSubscriptionByMemberAccountID(Integer accountID);

    public Iterable<AdvisorSubscription> getSubscriptionByMemberID(Integer memberID);

    public Iterable<AdvisorSubscription> getSubscriptionByAdvisorIDAndMonth(Integer advisorID, String month);

    public AdvisorSubscription createSubscriptionTransaction(CreateSubscriptionTransactionRequest createRequest, Integer accountID);

    public List<AdvisorSubscription> getSubscriptionByMemberAdvisor(Integer accountID);

    public List<Member> getCurrentMemeberOfAdvisor(Integer accountID);

    public AdvisorDetailsResponse getAdvisorOfMember(Integer accountID);

    public void updateSubscriptionStatus();

    public List<AdvisorSubscriptionSummary> getAdvisorSubscriptionSummaryByMonth();

    public List<CountSubscriptionResponse> countTotalSubscriptionIn6Months();
}
