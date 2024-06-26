/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Commission;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface CommissionRepository extends JpaRepository<Commission, Integer> {

    Commission findByAdvisor_AdvisorIDAndExpectedPaymentDate(Integer advisorID, LocalDate expectedPaymentDate);

    public Iterable<Commission> findByAdvisor_AdvisorID(Integer advisorID);

}
