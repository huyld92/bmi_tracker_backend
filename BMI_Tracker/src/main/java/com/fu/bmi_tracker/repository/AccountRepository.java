/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Account;
import com.fu.bmi_tracker.payload.response.AccountResponse;
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
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByEmailAndIsActiveTrue(String email);

    public boolean existsByEmail(String email);

    public boolean existsByPhoneNumber(String phoneNumber);

    @Query("SELECT new com.fu.bmi_tracker.payload.response.AccountResponse(a.accountID, a.fullName, a.email, a.phoneNumber, r.roleName, a.isActive) "
            + "FROM Account a JOIN a.role r")
    public List<AccountResponse> findAllAccountResponse();
}
