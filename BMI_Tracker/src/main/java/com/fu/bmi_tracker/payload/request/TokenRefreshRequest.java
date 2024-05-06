/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author Duc Huy
 */
@Data
public class TokenRefreshRequest {

    @NotBlank
    private String refreshToken;
}
