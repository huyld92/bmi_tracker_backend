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
public class MemberBmiResponse {

    private Integer memberID;
    private String accountPhoto;
    private String email;
    private String fullName;
    private LocalDate birthday;
    private Integer height;
    private Integer weight;

    public MemberBmiResponse(Member member, Integer height, Integer weight) {
        this.memberID = member.getMemberID();
        this.accountPhoto = member.getAccount().getAccountPhoto();
        this.email = member.getAccount().getEmail();
        this.fullName = member.getAccount().getFullName();
        this.birthday = member.getAccount().getBirthday();
        this.height = height;
        this.weight = weight;
    }

}
