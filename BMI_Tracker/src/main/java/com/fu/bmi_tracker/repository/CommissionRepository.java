/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Commission;
import com.fu.bmi_tracker.payload.response.CommissionSummaryResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface CommissionRepository extends JpaRepository<Commission, Integer> {

    Optional<Commission> findByAdvisor_AdvisorIDAndExpectedPaymentDate(Integer advisorID, LocalDate expectedPaymentDate);

    public Iterable<Commission> findByAdvisor_AdvisorID(Integer advisorID);

    @Query("SELECT c FROM Commission c WHERE  c.advisor.advisorID = :advisorID AND c.expectedPaymentDate < :currentDate")
    List<Commission> findAllBeforeCurrentDate(Integer advisorID, LocalDate currentDate);

    @Query("SELECT c FROM Commission c "
            + "WHERE c.advisor.advisorID = :advisorID "
            + "AND c.expectedPaymentDate >= :startDate "
            + "AND c.expectedPaymentDate <= :endDate "
            + "ORDER BY c.expectedPaymentDate DESC")
    List<Commission> findCommissionsForAdvisorInLastSixMonths(Integer advisorID, LocalDate startDate, LocalDate endDate);

    List<Commission> findByAdvisor_AdvisorIDAndExpectedPaymentDateBetweenOrderByExpectedPaymentDateDesc(
            Integer advisorID, LocalDate startDate, LocalDate endDate);

    @Query("SELECT new com.fu.bmi_tracker.payload.response.CommissionSummaryResponse(YEAR(c.expectedPaymentDate), MONTH(c.expectedPaymentDate), COUNT(c)) "
            + "FROM Commission c "
            + "WHERE c.expectedPaymentDate  BETWEEN :startDate AND :endDate "
            + "GROUP BY YEAR(c.expectedPaymentDate), MONTH(c.expectedPaymentDate) "
            + "ORDER BY YEAR(c.expectedPaymentDate) DESC, MONTH(c.expectedPaymentDate) DESC")
    List<CommissionSummaryResponse> countCommissionsPerMonth(LocalDate startDate, LocalDate endDate);
}
