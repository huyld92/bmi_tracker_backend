/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.exceptions.TokenException;
import com.fu.bmi_tracker.payload.request.LoginRequest;
import com.fu.bmi_tracker.payload.request.RegisterRequest;
import com.fu.bmi_tracker.payload.response.LoginResponse;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.model.entities.Account;
import com.fu.bmi_tracker.model.entities.Role;
import com.fu.bmi_tracker.model.enums.EGender;
import com.fu.bmi_tracker.model.enums.ERole;
import com.fu.bmi_tracker.repository.AccountRepository;
import com.fu.bmi_tracker.repository.RoleRepository;
import com.fu.bmi_tracker.security.jwt.JwtUtils;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.EmailDetails;
import com.fu.bmi_tracker.model.entities.RefreshToken;
import com.fu.bmi_tracker.payload.request.TokenRefreshRequest;
import com.fu.bmi_tracker.payload.response.TokenRefreshResponse;
import com.fu.bmi_tracker.services.EmailService;
import com.fu.bmi_tracker.services.RefreshTokenService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
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
    private EmailService emailService;
    
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

    @Autowired
    RefreshTokenService refreshTokenService;

    @Operation(
            summary = "login by phone number and password",
            description = "Authenticate accounts by phone number and password. Returned will be account information and will not include a password",
            tags = {"Authentication"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Account.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "400", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "401", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "403", content = {
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
        String jwt = jwtUtils.generateJwtToken(loginRequest.getEmail());

        CustomAccountDetailsImpl accountDetails = (CustomAccountDetailsImpl) authentication.getPrincipal();

        // Lấy danh sách quyền của người dùng
        Set<String> authorities = accountDetails.getAuthorities().stream()
                .map(Object::toString)
                .collect(Collectors.toSet());
        List<String> stringList = new ArrayList(authorities);

        // Lấy quyền đầu tiên và gán nó cho biến role
        String role = stringList.get(0);

        refreshTokenService.findByToken(role);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(accountDetails.getId());

        return ResponseEntity.ok(new LoginResponse(
                accountDetails.getId(),
                accountDetails.getEmail(),
                role, refreshToken.getToken(), jwt
        ));
    }

    @Operation(
            summary = "Register",
            description = "Register account with default role user",
            tags = {"Authentication"})
    @ApiResponses({
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "401", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
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

        Role accountRole = roleRepository.findByRoleName(ERole.ROLE_USER);
//                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        // Create new user's account
        Account account = new Account(registerRequest.getFullName(),
                registerRequest.getEmail(), registerRequest.getPhoneNumber(),
                encoder.encode(registerRequest.getPassword()),
                EGender.Other, registerRequest.getBirthday(),
                true, accountRole);
        // xóa khi verfied email có
        account.setIsVerified(true);
        
        //Save thông tin account xuống database
        accountRepository.save(account);
        
        //Create New User in firebase
        try {
            CreateRequest createRequest = new CreateRequest();
            createRequest.setEmail(registerRequest.getEmail());
            createRequest.setEmailVerified(false); 
            createRequest.setPassword(registerRequest.getPassword());
            
            //Creating new user
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(createRequest);
            
            //Generate Veritification Link
            String link = FirebaseAuth.getInstance().generateEmailVerificationLink(registerRequest.getEmail());
            
            //Send mail with vertificaiton link
            EmailDetails details = new EmailDetails(userRecord.getEmail(), link, registerRequest.getFullName());
            emailService.sendSimpleMail(details);
        }
        catch (Exception e) {
            System.out.println("Error with firebase account creation");
        }
        
        return ResponseEntity.ok(new MessageResponse("Account registered successfully!"));
    }

    @Operation(
            summary = "Refresh token",
            description = "Using refresh token to get new access token when expired",
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
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        String requestRefreshToken = tokenRefreshRequest.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(refreshToken -> refreshToken.getAccount())
                .map(account -> {
                    String token = jwtUtils.generateJwtToken(account.getEmail());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenException(requestRefreshToken,
                "Refresh token is not in database!"));
    }

    @Operation(
            summary = "Log out",
            description = "Log out of the system",
            tags = {"Authentication"})
    @ApiResponses({
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "401", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        CustomAccountDetailsImpl accountDetails = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Integer accountID = accountDetails.getId();
        refreshTokenService.deleteByAccountID(accountID);
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }
}
