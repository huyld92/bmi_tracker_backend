/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Booking;
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
public class BookingResponse {

    private Integer bookingID;
    private String bookingNumber;
    private String bookingDescription;
    private BigDecimal amount;
    private LocalDateTime bookingDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer memberID;
    private Integer advisorID;
    private String bookingStatus;

    public BookingResponse(Booking booking) {
        this.bookingID = booking.getBookingID();
        this.bookingNumber = booking.getBookingNumber();
        this.bookingDescription = booking.getBookingDescription();
        this.amount = booking.getBookingAmount();
        this.bookingDate = booking.getBookingDate();
        this.startDate = booking.getStartDate();
        this.endDate = booking.getEndDate();
        this.memberID = booking.getMember().getMemberID();
        this.advisorID = booking.getAdvisor().getAdvisorID();
        this.bookingStatus = booking.getBookingStatus().toString();
    }

}
