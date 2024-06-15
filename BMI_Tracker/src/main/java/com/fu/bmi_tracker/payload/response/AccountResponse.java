/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Account;
import com.fu.bmi_tracker.model.entities.Role;
import com.fu.bmi_tracker.model.enums.ERole;
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
public class AccountResponse {

    private Integer accountID;
    private String email;
    private String fullName;
    private String phoneNumber;
    private List<ERole> roleNames;
//    private ERole roleName;

    private Boolean isActive;
    // private String status;

    public AccountResponse(Account account) {
        this.accountID = account.getAccountID();
        this.email = account.getEmail();
        this.fullName = account.getFullName();
        this.phoneNumber = account.getPhoneNumber();
        this.roleNames = new ArrayList<>();
        for (Role role : account.getRoles()) {
            // Add the role name to the roleNames Set
            roleNames.add(role.getRoleName());
        }
        account.getRoles();
        this.isActive = account.getIsActive();
    }
//    public AccountResponse(Account account) {
//        this.accountID = account.getAccountID();
//        this.email = account.getEmail();
//        this.fullName = account.getFullName();
//        this.phoneNumber = account.getPhoneNumber();
////        this.roleName = account.getRoles().iterator().next().getRoleName();
//        // Handle roles if an account can have multiple roles
//        if (account.getRoles() != null && !account.getRoles().isEmpty()) {
//            this.roleName = account.getRoles().iterator().next().getRoleName();
//        }
//        this.isActive = account.getIsActive();
//    }

}
