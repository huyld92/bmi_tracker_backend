/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

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
public class LoginResponse {

    private Integer accountID;
    private String email;
    private String accountPhoto;
    private ERole role;
//    private String role;
    private String refreshToken;
    private String accessToken;

    public LoginResponse(String accessToken, String accountPhoto, Integer accountID, String email, ERole role) {
        this.accessToken = accessToken;
        this.accountID = accountID;
        this.accountPhoto = accountPhoto;
        this.email = email;
        this.role = role;
    }

}
