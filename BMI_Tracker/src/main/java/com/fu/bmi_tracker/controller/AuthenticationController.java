/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi.tracker.payload.request.LoginRequest;
import com.fu.bmi.tracker.payload.request.RegisterRequest;
import com.fu.bmi.tracker.payload.response.LoginResponse;
import com.fu.bmi.tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.model.entities.Account;
import com.fu.bmi_tracker.model.entities.Role;
import com.fu.bmi_tracker.model.enums.EGender;
import com.fu.bmi_tracker.model.enums.ERole;
import com.fu.bmi_tracker.repository.AccountRepository;
import com.fu.bmi_tracker.repository.RoleRepository;
import com.fu.bmi_tracker.security.jwt.JwtUtils;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Authentication", description = "Authentication management APIs")
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Operation(
            summary = "login by phone number and password",
            description = "Authenticate accounts by phone number and password. Returned will be account information and will not include a password",
            tags = {"Authentication"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Account.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "401", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // generate mã jwt 
        String jwt = jwtUtils.generateJwtToken(authentication);

        System.out.println(jwt);

        CustomAccountDetailsImpl accountDetails = (CustomAccountDetailsImpl) authentication.getPrincipal();

        // Lấy danh sách quyền của người dùng
        Set<String> authorities = accountDetails.getAuthorities().stream()
                .map(Object::toString)
                .collect(Collectors.toSet());
        List<String> stringList = new ArrayList(authorities);

        // Lấy quyền đầu tiên và gán nó cho biến role
        String role = stringList.get(0);

        return ResponseEntity.ok(new LoginResponse(jwt,
                accountDetails.getId(),
                accountDetails.getEmail(),
                role));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

        if (accountRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        if (accountRepository.existsByPhoneNumber(registerRequest.getPhoneNumber())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Phone number is already taken!"));
        }

        Role accountRole = roleRepository.findByRoleName(ERole.ROLE_CUSTOMER);
//                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        // Create new user's account
        Account account = new Account(registerRequest.getFullName(),
                registerRequest.getEmail(), registerRequest.getPhoneNumber(),
                encoder.encode(registerRequest.getPassword()),
                EGender.Other, registerRequest.getBirthday(),
                "Active", accountRole);

        accountRepository.save(account);

        return ResponseEntity.ok(new MessageResponse("Account registered successfully!"));
    }

}
