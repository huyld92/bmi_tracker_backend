/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import com.fu.bmi_tracker.model.enums.EGender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class UpdateProfileRequest {

    @NotBlank(message = "Full name must not blank")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    @Pattern(regexp = "^[a-zA-Z]+( [a-zA-Z]+)*$", message = "Invalid full name")
    @Schema(name = "fullName", example = "Nguyen Van A")
    private String fullName;

    @Size(min = 10, max = 13, message = "Phone must be from 10 to 13 characters.")
    @Pattern(regexp = "^(0)([3|5|7|8|9])+([0-9]{8})$", message = "Invalid phone number format (0907111111)")
    @Schema(name = "phoneNumber", example = "0907111111")
    private String phoneNumber;

    @Schema(description = "URL of the account photo(NULL)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String accountPhoto;

    @Schema(name = "gender", examples = {"Male", "Female"})
    private EGender gender;

    @NotNull
    @Past
    @Schema(name = "birthday", example = "1990-01-01")
    private LocalDate birthday;
}
