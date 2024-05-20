/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import lombok.Data;

/**
 *
 * @author Duc Huy
 */
@Data
public final class EmailDetails {
    // Class data members

    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;

    public EmailDetails(String recipient, String verificationLink, String fullName) {
        this.subject = "Verify your email for BMI Tracker";
        this.recipient = recipient;
        this.msgBody = VerificationEmailBodyTemplateGenerator(verificationLink, fullName);
    }

    public EmailDetails(String recipient, String subject, String password, String fullName) {
        this.recipient = recipient;
        this.subject = subject;
        this.msgBody = sendNewPasswordTemplateGenerator(recipient, password, fullName);
    }

    public String VerificationEmailBodyTemplateGenerator(String verificationLink, String fullname) {
        return "Hello " + fullname + ","
                + "\n\n" + "Follow this link to verify your email address."
                + "\n\n" + verificationLink
                + "\n\n" + "If you didn’t ask to verify this address, you can ignore this email."
                + "\n\n" + "Thanks,"
                + "\n\n" + "BMI Team";
    }

    public String sendNewPasswordTemplateGenerator(String email, String password, String fullname) {
        return "Dear " + fullname + ","
                + "\n\n" + "An account has been created for you. Here are your login details:\n"
                + "Username: " + email + "\n"
                + "Password: " + password + "\n\n"
                + "For security reasons, please change your password after your first login.\n\n"
                + "Best Regards,\n\n"
                + "BMI Team";

    }
}
