/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.enums.EGender;
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
public class AdvisorResponse {

    private String linkPhoto = "https://png.pngtree.com/element_our/20200610/ourmid/pngtree-black-default-avatar-image_2237212.jpg";

    private String email;
    private String fullName;
    private String phoneNumber;
    private EGender gender;
    private LocalDate birthday;

    public AdvisorResponse(String email, String fullName, String phoneNumber, EGender gender, LocalDate birthday) {
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthday = birthday;
    }

    
}
