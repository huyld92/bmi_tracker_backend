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
public class AdvisorDetailsResponse {

    private Integer accountID;
    private Integer advisorID;
    private String accountPhoto;
    private String email;
    private String fullName;
    private String phoneNumber;
    private EGender gender;
    private LocalDate birthday;
    private Integer totalSubscription;
    private Integer totalMenuCreated;
    private Integer totalWorkoutCreated;

    public AdvisorDetailsResponse(Advisor advisor, Integer totalMenuCreated, Integer totalWorkoutCreated) {
        this.accountID = advisor.getAccount().getAccountID();
        this.advisorID = advisor.getAdvisorID();
        this.accountPhoto = advisor.getAccount().getAccountPhoto();
        this.email = advisor.getAccount().getEmail();
        this.fullName = advisor.getAccount().getFullName();
        this.phoneNumber = advisor.getAccount().getPhoneNumber();
        this.gender = advisor.getAccount().getGender();
        this.birthday = advisor.getAccount().getBirthday();
        this.totalSubscription = advisor.getTotalSubscription();
        this.totalMenuCreated = totalMenuCreated;
        this.totalWorkoutCreated = totalWorkoutCreated;

    }
}
