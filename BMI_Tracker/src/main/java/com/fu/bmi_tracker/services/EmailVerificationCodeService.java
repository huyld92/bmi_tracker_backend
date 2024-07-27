/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.EmailVerificationCode;

/**
 *
 * @author BaoLG
 */
public interface EmailVerificationCodeService extends GeneralService<EmailVerificationCode> {

    //return email for address to update account status
    public EmailVerificationCode checkVerificationCode(String verificationCode);
}
