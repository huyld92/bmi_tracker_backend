/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Account;
import com.fu.bmi_tracker.model.entities.EmailDetails;
import com.fu.bmi_tracker.model.entities.EmailVerificationCode;
import com.fu.bmi_tracker.repository.AccountRepository;
import com.fu.bmi_tracker.services.EmailService;
import com.fu.bmi_tracker.services.EmailVerificationCodeService;
import java.util.Optional;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public void verificationEmail(@RequestParam("oobCode") String oobCode, HttpServletResponse response) throws IOException {
        // gọi service kiểm tra valid code
        EmailVerificationCode emailVerificationCode = verificationService.checkVerificationCode(oobCode);
        // code hêt hạn hoặc không match
        if (emailVerificationCode != null) {
            // kiểm tra code đã sử dụng chưa
            if (emailVerificationCode.getIsVerified()) {
                response.sendRedirect("http://localhost:3000/auth/account-verified");
            } else {
                Optional<Account> member = accountRepository.findByEmail(emailVerificationCode.getEmail());
                if (!member.isEmpty()) {
                    // cập nhật trạng thái verified của account
                    member.get().setIsVerified(Boolean.TRUE);
                    accountRepository.save(member.get());
                    // cập nhật trạng thái verified của code
                    emailVerificationCode.setIsVerified(Boolean.TRUE);
                    verificationService.save(emailVerificationCode);
                }
                response.sendRedirect("http://localhost:3000/auth/verification-success");
            }
        } else {
            response.sendRedirect("http://localhost:3000/auth/verification-failed");
        }
    }
}
