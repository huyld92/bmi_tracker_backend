/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Booking;
import com.fu.bmi_tracker.model.enums.EBookingStatus;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByMemberMemberID(Integer memberID);

    List<Booking> findByMember_Account_AccountID(Integer accountID);

    List<Booking> findByAdvisor_AdvisorIDAndBookingDateBetweenOrderByBookingDateDesc(Integer advisorID, LocalDateTime startOfMonth, LocalDateTime endOfMonth);

    List<Booking> findByAdvisor_AdvisorIDOrderByBookingDateDesc(Integer advisorID);

    List<Booking> findByAdvisor_Account_AccountIDAndEndDateGreaterThan(Integer accountID, LocalDate currentDate);

    Optional<Booking> findByMember_Account_AccountIDAndBookingStatus(Integer accountID, EBookingStatus bookingStatus);

    @Transactional
    @Modifying
    @Query("UPDATE Booking b SET b.bookingStatus = 'FINISHED' WHERE b.endDate < :today AND  b.bookingStatus <> 'FINISHED'")
    void updateExpiredBookings(LocalDate today);

    @Transactional
    @Modifying
    @Query("UPDATE Booking b SET b.bookingStatus = 'PENDING' WHERE b.startDate = :today")
    void updatePendingBookings(LocalDate today);
}
