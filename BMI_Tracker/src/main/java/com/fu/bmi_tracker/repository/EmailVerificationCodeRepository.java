/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.EmailVerificationCode;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author BaoLG
 */
@Repository
public interface EmailVerificationCodeRepository extends JpaRepository<EmailVerificationCode, Integer>{
    
    //@Query("SELECT * FROM VerificationCode m WHERE m.VerificationCode = :verificationCode")
    public Optional<EmailVerificationCode> findByVerificationCode(String verificationCode);
}
