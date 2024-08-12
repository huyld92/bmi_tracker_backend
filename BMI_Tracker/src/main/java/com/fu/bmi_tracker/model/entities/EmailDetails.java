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
//    private String attachment;

    public EmailDetails() {
    }

    public EmailDetails(String recipient, String verificationLink, String fullName) {
        this.subject = "Verify your email for BMI Tracker";
        this.recipient = recipient;
        this.msgBody = VerificationEmailBodyTemplateGenerator(verificationLink, fullName);
    }

    public EmailDetails(String recipient, String subject, String password, String fullName) {
        this.recipient = recipient;
        this.subject = subject;
        this.msgBody = sendNewPasswordAccountTemplateGenerator(recipient, password, fullName);
    }

    public String VerificationEmailBodyTemplateGenerator(String verificationLink, String fullname) {
        return "Hello " + fullname + ","
                + "\n\n" + "Follow this link to verify your email address."
                + "\n\n" + verificationLink
                + "\n\n" + "If you didn’t ask to verify this address, you can ignore this email."
                + "\n\n" + "Thanks,"
                + "\n\n" + "BMI Team";
    }

    public String sendNewPasswordAccountTemplateGenerator(String email, String password, String fullname) {
        return "Dear " + fullname + ","
                + "\n\n" + "An account has been created for you. Here are your login details:\n"
                + "Username: " + email + "\n"
                + "Password: " + password + "\n\n"
                + "For security reasons, please change your password after your first login.\n\n"
                + "Best Regards,\n\n"
                + "BMI Team";
    }

    public String sendResetPasswordTemplateGenerator(String email, String password, String fullname) {
        return "Dear " + fullname + ",\n\n"
                + "We received a request to reset the password for your BMI account. Here are your new login details:\n\n"
                + "Username: " + email + "\n"
                + "Password: " + password + "\n\n"
                + "For security reasons, we recommend that you log in and change this temporary password as soon as possible. "
                + "If you did not request a password reset, please contact our support team immediately at [Support Email: huyddse63197@fpt.edu.vn].\n\n"
                + "Thank you for choosing BMI.\n\n"
                + "Best regards,\n\n"
                + "BMI Support Team";
    }

    public String sendActiveAdvisorTemplateGenerator(String fullname) {
        return "Dear " + fullname + ",\n\n"
                + "We are pleased to inform you that your account has been successfully activated.\n"
                + "You can now log in and start exploring all the features and services we offer.\n"
                + "If you did not request a password reset, please contact our support team immediately at [Support Email: huyddse63197@fpt.edu.vn].\n\n"
                + "Thank you for choosing BMI.\n\n"
                + "Best regards,\n\n"
                + "BMI Support Team";
    }

}
