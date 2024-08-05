/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Account;
import com.fu.bmi_tracker.model.entities.Role;
import com.fu.bmi_tracker.model.enums.ERole;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
public class AdvisorProfileResponse {

    private Integer accountID;
    private String email;
    private String fullName;
    private String accountPhoto;
    private String phoneNumber;
    private String gender;
    private String bankName;
    private String bankNumber;
    private LocalDate birthday;
    private List<ERole> roleNames;
//    private ERole roleName;

    private Boolean isActive;
    // private String status;

    public AdvisorProfileResponse(Account account, String bankName, String bankNumber) {
        this.accountID = account.getAccountID();
        this.email = account.getEmail();
        this.fullName = account.getFullName();
        this.accountPhoto = account.getAccountPhoto();
        this.phoneNumber = account.getPhoneNumber();
        this.gender = account.getGender().name();
        this.birthday = account.getBirthday();
        this.bankName = bankName;
        this.bankNumber = bankNumber;
        this.roleNames = new ArrayList<>();
        for (Role role : account.getRoles()) {
            // Add the role name to the roleNames Set
            roleNames.add(role.getRoleName());
        }
        this.isActive = account.getIsActive();
    }

}
