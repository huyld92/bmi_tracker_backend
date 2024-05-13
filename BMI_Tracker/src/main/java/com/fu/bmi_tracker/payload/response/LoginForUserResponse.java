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
public class LoginForUserResponse {

    private Integer userID;
    private String email;
    private String fullname;
    private String gender;
    private String phonenumber;
    private int height;
    private int weight;
    private int age;
    private double BMI;
    private double BMR;
    private double TDEE;
    private String activityLevel;
    private String refreshToken;
    private String accessToken;

}
