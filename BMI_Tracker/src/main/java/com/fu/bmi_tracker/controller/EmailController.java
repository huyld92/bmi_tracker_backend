/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Account;
import com.fu.bmi_tracker.model.entities.EmailDetails;
import com.fu.bmi_tracker.repository.AccountRepository;
import com.fu.bmi_tracker.services.EmailService;
import com.fu.bmi_tracker.services.EmailVerificationCodeService;
import com.fu.bmi_tracker.services.UserService;
import java.util.Optional;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author Duc Huy
 */
@Hidden
@RestController
public class EmailController {

    @Autowired
    private EmailVerificationCodeService verificationService;
    
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    private EmailService emailService;
    // Sending a simple Email

    @PostMapping("/sendMail")
    public String
            sendMail(@RequestBody EmailDetails details) {
        String status
                = emailService.sendSimpleMail(details);

        return status;
    }

    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(
            @RequestBody EmailDetails details) {
        String status
                = emailService.sendMailWithAttachment(details);

        return status;
    }
    
    @GetMapping("/verificationEmail")
    public RedirectView verificationEmail(@RequestParam("oobCode") String oobCode) {
       
        String verificationEmail = verificationService.checkVerificationCode(oobCode);
        if (verificationEmail != null) {
            
            Optional<Account> user = accountRepository.findByEmail(verificationEmail);
            if (!user.isEmpty()) {
                user.get().setIsVerified(Boolean.TRUE);
                accountRepository.save(user.get());
            }
            return new RedirectView("/success");  // redirect to page afte verification success.
        }
        else {
            System.out.println(oobCode);
            return new RedirectView("/fail");  // redirect to page afte verification success.
        }
    }
}
