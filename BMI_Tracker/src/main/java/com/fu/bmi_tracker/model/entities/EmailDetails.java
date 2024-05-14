/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Duc Huy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public String VerificationEmailBodyTemplateGenerator(String verificationLink, String fullname) {
        return "Hello " + fullname + ","
                + "\n\n" + "Follow this link to verify your email address."
                + "\n\n" + verificationLink
                + "\n\n" + "If you didn’t ask to verify this address, you can ignore this email."
                + "\n\n" + "Thanks,"
                + "\n\n" + "BMI Team";
    }
}
