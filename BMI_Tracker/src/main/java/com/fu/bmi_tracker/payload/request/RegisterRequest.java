/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

import com.fu.bmi_tracker.model.enums.EGender;

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
public class RegisterRequest {

    @NotBlank(message = "Full name must not blank")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    @Schema(name = "fullName", example = "Nguyen Van A")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email format")
    @Schema(name = "email", example = "example@gmail.com")
    private String email;

    @Size(min = 10, max = 13, message = "Phone must be from 10 to 13 characters.")
    @Pattern(regexp = "^(0)([3|5|7|8|9])+([0-9]{8})$", message = "Invalid phone number format (0907111111)")
    @Schema(name = "phoneNumber", example = "0907111111")
    private String phoneNumber;

    @NotBlank(message = "The password is required.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$", message = "Password must be 8 characters long and combination of uppercase letters, lowercase letters, numbers, special characters.")
    @Schema(name = "password", example = "As123456@")
    private String password;

    @Schema(name = "gender", examples = {"Male"})
    private EGender gender;

    @NotNull
    @Past
    @Schema(name = "birthday", example = "1990-01-01")
    private LocalDate birthday;

}
