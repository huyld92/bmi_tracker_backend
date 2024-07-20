/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.enums.EGender;
import java.time.LocalDate;
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
public class AdvisorResponse {

    private Integer advisorID;
    private String accountPhoto;
    private String email;
    private String fullName;
    private String phoneNumber;
    private EGender gender;
    private LocalDate birthday;
    private Integer totalSubscription;
//
//    public AdvisorResponse(Integer advisorID, String accountPhoto, String email, String fullName, String phoneNumber, EGender gender, LocalDate birthday) {
//        this.advisorID = advisorID;
//        this.accountPhoto = accountPhoto;
//        this.email = email;
//        this.fullName = fullName;
//        this.phoneNumber = phoneNumber;
//        this.gender = gender;
//        this.birthday = birthday;
//    }

    public AdvisorResponse(Advisor advisor) {
        this.advisorID = advisor.getAdvisorID();
        this.accountPhoto = advisor.getAccount().getAccountPhoto();
        this.email = advisor.getAccount().getEmail();
        this.fullName = advisor.getAccount().getFullName();
        this.phoneNumber = advisor.getAccount().getPhoneNumber();
        this.gender = advisor.getAccount().getGender();
        this.birthday = advisor.getAccount().getBirthday();
        this.totalSubscription = advisor.getTotalSubscription();
    }

}
