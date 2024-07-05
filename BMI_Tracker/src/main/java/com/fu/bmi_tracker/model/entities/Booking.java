/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.model.enums.EBookingStatus;
import com.fu.bmi_tracker.payload.request.BookingRequest;
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
import java.math.BigDecimal;
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
@Table(name = "[Booking]")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookingID", nullable = false)
    private Integer bookingID;

    @Column(name = "BookingDescription", nullable = false)
    private String bookingDescription;

    @Column(name = "BookingNumber", nullable = false)
    private String bookingNumber;

    @Column(name = "BookingAmount", nullable = false)
    private BigDecimal bookingAmount;

    @Column(name = "BookingDate", nullable = false)
    private LocalDateTime bookingDate;

    @Column(name = "StartDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "EndDate", nullable = false)
    private LocalDate endDate;

    @Column(name = "BookingStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private EBookingStatus bookingStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MemberID", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AdvisorID", nullable = false)
    private Advisor advisor;

    @Column(name = "CommissionID", nullable = false)
    private Integer commissionID;

    @Column(name = "TransactionID", nullable = false, unique = true)
    private Integer transactionID;

    public Booking(BookingRequest bookingRequest,
            LocalDate startDate,
            LocalDate endDate,
            Integer memberID,
            Advisor advisor,
            int transactionID,
            int commissionID) {
        this.bookingDescription = bookingRequest.getDescription();
        this.bookingNumber = bookingRequest.getBookingNumber();
        this.bookingAmount = bookingRequest.getAmount();
        this.bookingDate = LocalDateTime.now(ZoneId.of("GMT+7"));
        this.startDate = startDate;
        this.endDate = endDate;
        this.member = new Member();
        this.member.setMemberID(memberID);
        this.advisor = advisor;
        this.bookingStatus = checkBookingStatus(startDate);
        this.commissionID = commissionID;
        this.transactionID = transactionID;
    }

    private EBookingStatus checkBookingStatus(LocalDate startDate) {
        LocalDate now = LocalDate.now();
        if (now.isBefore(startDate)) {
            //ngày hiện tại trước startDate
            return EBookingStatus.NOT_STARTED;
        } else if (now.equals(startDate)) {
            //ngày hiện tại bằng  startDate
            return EBookingStatus.PENDING;
        } else {
            return EBookingStatus.CANCELLED;
        }
    }
}
