/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Member;
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
public class MemberResponse {

    private Integer accountID;
    private Integer memberID;
    private String email;
    private String accountPhoto;
    private String fullName;
    private String gender;
    private String phoneNumber;
    private LocalDate birthday;
    private Integer weight;
    private Integer targetWeight;
    private String dietaryPreference;

    public MemberResponse(Member member, Integer weight) {
        this.accountID = member.getAccount().getAccountID();
        this.memberID = member.getMemberID();
        this.email = member.getAccount().getEmail();
        this.accountPhoto = member.getAccount().getAccountPhoto();
        this.fullName = member.getAccount().getFullName();
        this.gender = member.getAccount().getGender().toString();
        this.phoneNumber = member.getAccount().getPhoneNumber();
        this.birthday = member.getAccount().getBirthday();
        this.weight = weight;
        this.targetWeight = member.getTargetWeight();
        this.dietaryPreference = member.getDietaryPreference();
    }

}
