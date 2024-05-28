package com.fu.bmi_tracker.payload.request;

import java.time.LocalDate;

import com.fu.bmi_tracker.model.enums.EGender;
import com.fu.bmi_tracker.model.enums.ERole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountRequest {

    @NotBlank(message = "Full name must not blank")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    @Pattern(regexp = "^[a-zA-Z]+( [a-zA-Z]+)*$", message = "Invalid full name")
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

    @NotBlank(message = "Gender is required")
    @Schema(name = "gender", examples = {"Male", "Female"})
    private EGender gender;

    @NotNull
    @Past
    @Schema(name = "birthday", example = "1990-01-01")
    private LocalDate birthday;

    @NotNull(message = "Role is required")
    @Schema(name = "role", example = "ROLE_ADVISOR")
    private ERole role;
}
