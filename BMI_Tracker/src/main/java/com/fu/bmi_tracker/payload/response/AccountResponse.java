/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Account;
import com.fu.bmi_tracker.model.enums.ERole;
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
public class AccountResponse {

    private Integer accountID;
    private String email;
    private String fullName;
    private String phoneNumber;
    private ERole roleName;
    // private String status;

    public AccountResponse(Account account) {
        this.accountID = account.getAccountID();
        this.email = account.getEmail();
        this.fullName = account.getFullName();
        this.phoneNumber = account.getPhoneNumber();
        this.roleName = account.getRole().getRoleName();
    }

}
