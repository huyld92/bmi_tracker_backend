/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Account;
import jakarta.transaction.Transactional;
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
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByEmailAndIsActiveTrue(String email);
    
    String findDeviceTokenByAccountID(Integer accountID);

    public boolean existsByEmail(String email);

    public boolean existsByPhoneNumber(String phoneNumber);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.deviceToken = :deviceToken WHERE a.accountID = :accountID")
    public void updateDeviceToken(Integer accountID, String deviceToken);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.accountPhoto = :imageLink WHERE a.accountID = :accountID")
    public void updateAccountPhoto(Integer accountID, String imageLink);

}
