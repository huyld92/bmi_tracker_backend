/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Commission;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface CommissionRepository extends JpaRepository<Commission, Integer> {

    Commission findByAdvisor_AdvisorIDAndExpectedPaymentDate(Integer advisorID, LocalDate expectedPaymentDate);

    public Iterable<Commission> findByAdvisor_AdvisorID(Integer advisorID);

    @Query("SELECT COALESCE(SUM(c.commissionAmount), 0) "
            + "FROM Commission c "
            + "WHERE c.advisor.advisorID = :advisorId "
            + "AND FUNCTION('YEAR', c.expectedPaymentDate) = :year "
            + "AND FUNCTION('MONTH', c.expectedPaymentDate) = :month ")
    BigDecimal getTotalCommissionByAdvisorIdAndMonth(
            @Param("advisorId") Integer advisorId,
            @Param("year") int year,
            @Param("month") int month);

}
