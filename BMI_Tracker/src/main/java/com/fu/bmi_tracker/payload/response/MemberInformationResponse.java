/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

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
public class MemberInformationResponse {

    private Integer accountID;
    private Integer memberID;
    private String email;
    private String accountPhoto;
    private String fullName;
    private String gender;
    private String phoneNumber;
    private LocalDate endDateOfPlan;
    private LocalDate birthday;
    private Integer height;
    private Integer weight;
    private Integer targetWeight;
    private Integer age;
    private Double BMI;
    private String dietaryPreference;

}
