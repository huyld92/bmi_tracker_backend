/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.RefreshToken;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    public Optional<RefreshToken> findByToken(String token);

    public void deleteByAccount_AccountID(Integer accountID);

    public void deleteByExpiryDateLessThan(LocalDateTime now);
}
