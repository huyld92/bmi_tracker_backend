/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Advisor;
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
public class AdvisorSummaryMenuWorkout {

    private Integer advisorID;
    private String accountPhoto;
    private String email;
    private String fullName;
    private Integer totalMenu;
    private Integer totalWorkout;

    public AdvisorSummaryMenuWorkout(Advisor advisor, Integer totalMenu, Integer totalWorkout) {
        this.advisorID = advisor.getAdvisorID();
        this.accountPhoto = advisor.getAccount().getAccountPhoto();
        this.email = advisor.getAccount().getEmail();
        this.fullName = advisor.getAccount().getFullName();
        this.totalMenu = totalMenu;
        this.totalWorkout = totalWorkout;
    }

}
