/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.payload.response.AdvisorResponse;
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
public interface AdvisorRepository extends JpaRepository<Advisor, Integer> {

    public Optional<Advisor> findByAccount_AccountID(Integer accountID);

    @Query("SELECT new com.fu.bmi_tracker.payload.response.AdvisorResponse(adv.advisorID, a.accountPhoto, a.email, a.fullName, a.phoneNumber, a.gender, a.birthday) "
            + "FROM Account a JOIN Advisor adv ON a.accountID = adv.account.accountID AND adv.isActive = true")
    List<AdvisorResponse> findAllAdvisorsWithDetails();
}
