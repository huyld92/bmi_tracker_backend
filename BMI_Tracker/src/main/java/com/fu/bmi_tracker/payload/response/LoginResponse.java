/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

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
//    private ERole role;
    private String role;
    private String refreshToken;
    private String accessToken;

    public LoginResponse(String accessToken, Integer accountID, String email, String role) {
        this.accessToken = accessToken;
        this.accountID = accountID;
        this.email = email;
        this.role = role;
    }

}
