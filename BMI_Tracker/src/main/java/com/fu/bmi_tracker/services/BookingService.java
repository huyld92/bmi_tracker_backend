/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.Booking;
import com.fu.bmi_tracker.payload.request.CreateBookingTransactionRequest;
import com.fu.bmi_tracker.payload.response.AdvisorBookingSummary;
import com.fu.bmi_tracker.payload.response.AdvisorDetailsResponse;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface BookingService extends GeneralService<Booking> {

    public Iterable<Booking> getBookingByMemberAccountID(Integer accountID);

    public Iterable<Booking> getBookingByMemberID(Integer memberID);

    public Iterable<Booking> getBookingByAdvisorIDAndMonth(Integer advisorID, String month);

    public Booking createBookingTransaction(CreateBookingTransactionRequest createRequest, Integer accountID);

    public List<Booking> getBookingByMemberAdvisor(Integer accountID);

    public List<Member> getCurrentMemeberOfAdvisor(Integer accountID);

    public AdvisorDetailsResponse getAdvisorOfMember(Integer accountID);

    public void updateBookingStatus();

    public List<AdvisorBookingSummary> getAdvisorBookingSummaryByMonth();

}
