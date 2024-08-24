/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.AdvisorSubscription;
import com.fu.bmi_tracker.model.enums.ESubscriptionStatus;
import com.fu.bmi_tracker.payload.response.CountSubscriptionResponse;
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
public interface SubscriptionRepository extends JpaRepository<AdvisorSubscription, Integer> {

    List<AdvisorSubscription> findByMemberMemberID(Integer memberID);

    List<AdvisorSubscription> findByMember_Account_AccountID(Integer accountID);

    List<AdvisorSubscription> findByAdvisor_AdvisorIDAndSubscriptionDateBetweenOrderBySubscriptionDateDesc(Integer advisorID, LocalDateTime startOfMonth, LocalDateTime endOfMonth);

    List<AdvisorSubscription> findByAdvisor_AdvisorIDOrderBySubscriptionDateDesc(Integer advisorID);

    List<AdvisorSubscription> findByAdvisor_Account_AccountIDAndSubscriptionStatusAndEndDateGreaterThan(Integer accountID, ESubscriptionStatus subscriptionStatus, LocalDate currentDate);

    Optional<AdvisorSubscription> findByMember_Account_AccountIDAndSubscriptionStatus(Integer accountID, ESubscriptionStatus subscriptionStatus);

    @Transactional
    @Modifying
    @Query("UPDATE AdvisorSubscription as SET as.subscriptionStatus = 'FINISHED' WHERE as.endDate < :today AND  as.subscriptionStatus <> 'FINISHED'")
    void updateExpiredSubscriptions(LocalDate today);

    @Transactional
    @Modifying
    @Query("UPDATE AdvisorSubscription as SET as.subscriptionStatus = 'PENDING' WHERE as.startDate = :today")
    void updatePendingSubscriptions(LocalDate today);

    @Query("SELECT new com.fu.bmi_tracker.payload.response.CountSubscriptionResponse(YEAR(as.subscriptionDate), MONTH(as.subscriptionDate), COUNT(as)) "
            + "FROM AdvisorSubscription as "
            + "WHERE as.subscriptionDate BETWEEN :startDateTime AND :endDateTime "
            + "GROUP BY YEAR(as.subscriptionDate), MONTH(as.subscriptionDate) "
            + "ORDER BY YEAR(as.subscriptionDate) DESC, MONTH(as.subscriptionDate) DESC")
    public List<CountSubscriptionResponse> countTotalSubscriptionPerMonthInBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
