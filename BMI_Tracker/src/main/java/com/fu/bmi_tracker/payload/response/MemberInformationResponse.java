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
public class MemberInformationResponse {

    private Integer memberID;
    private String email;
    private String fullName;
    private String gender;
    private String phoneNumber;
    private int height;
    private int weight;
    private int age;
    private double BMI;
    private double BMR;
    private double TDEE;
}
