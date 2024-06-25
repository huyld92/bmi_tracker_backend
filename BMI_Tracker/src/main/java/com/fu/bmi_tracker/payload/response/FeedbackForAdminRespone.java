/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Feedback;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author BaoLG
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackForAdminRespone {

    private Integer feedbackID;

    private String title;

    private String type;

    private String description;

    private Boolean status;

    private Integer memberID;

    private String memberName;

    public FeedbackForAdminRespone(Feedback feedback) {
        this.feedbackID = feedback.getFeedbackID();
        this.title = feedback.getTitle();
        this.type = feedback.getType();
        this.description = feedback.getDescription();
        this.status = feedback.isStatus();
        this.memberID = feedback.getMember().getMemberID();
        this.memberName = feedback.getMember().getAccount().getFullName();
    }

}
