/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.EmailVerificationCode;
import com.fu.bmi_tracker.repository.EmailVerificationCodeRepository;
import com.fu.bmi_tracker.services.EmailVerificationCodeService;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author BaoLG
 */
@Service
public class EmailVerificationCodeServiceImpl implements EmailVerificationCodeService {

    @Autowired
    EmailVerificationCodeRepository repository;

    @Override
    public Iterable<EmailVerificationCode> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<EmailVerificationCode> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public EmailVerificationCode save(EmailVerificationCode t) {
        return repository.save(t);
    }

    @Override
    public EmailVerificationCode checkVerificationCode(String verificationCode) {
        Optional<EmailVerificationCode> code = repository.findByVerificationCode(verificationCode);
        if (!code.isPresent()) {
            System.out.println("---------------------------------------------");
            System.out.println("Repository khong tim duoc: " + verificationCode);
            System.out.println("---------------------------------------------");
            //Khong tim duoc code => code khong ton tai => verification fail
            return null;
        } else {
            //checkcode time out
            if (checkVerificationTimeOut(code.get().getCreationTime())) {
                //code time out verification fail
                return null;
            } else {
                //code valide in time => verification success.
                return code.get();
            }
        }
    }

    //false mean still in time
    //true mean time out
    private boolean checkVerificationTimeOut(LocalDateTime creationTime) {

        //Insert timeout logic here.
        return false;
    }
}
