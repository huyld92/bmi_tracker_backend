/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Account;
import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.EmailDetails;
import com.fu.bmi_tracker.model.enums.ERole;
import com.fu.bmi_tracker.payload.request.CreateAccountRequest;
import com.fu.bmi_tracker.payload.request.UpdateAccountRequest;
import com.fu.bmi_tracker.payload.response.AccountResponse;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.services.AccountService;
import com.fu.bmi_tracker.services.AdvisorService;
import com.fu.bmi_tracker.services.EmailService;
import com.fu.bmi_tracker.util.PasswordUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Account", description = "Account management APIs")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    
    @Autowired
    AccountService accountService;
    
    @Autowired
    EmailService emailService;
    
    @Autowired
    AdvisorService advisorService;
//    
//    @Autowired
//    PasswordUtils passwordUtils;
    
    @Autowired
    PasswordEncoder encoder;
    
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    
    @Operation(summary = "Create new account (ADMIN)", description = "Create new account with role name (ROLE_ADMIN, ROLE_USER, ROLE_ADVISOR)")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = AccountResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        // Generate default password and endcode to save
        // passwordUtils.generateRandomPassword(10);
        String defaultPassword = "123456";

        // Store to database
        Account accountSave = accountService.createAccount(createAccountRequest,
                encoder.encode(defaultPassword));

        // check result
        if (accountSave == null) {
            return new ResponseEntity<>("Failed to create new account", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // nếu account là advisor thì tạo thông tin mặc định cho advisor
        if (createAccountRequest.getRole() == ERole.ROLE_ADVISOR) {
            Advisor advisor = new Advisor(
                    accountSave.getAccountID(),
                    0, 
                    0, true);
            advisorService.save(advisor);
        }
        
        EmailDetails details = new EmailDetails(accountSave.getEmail(),
                "Your New Account Password",
                defaultPassword,
                accountSave.getFullName());
        emailService.sendSimpleMail(details);
        
        AccountResponse accountResponse = new AccountResponse(accountSave);
        
        return new ResponseEntity<>(accountResponse, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Get all account (ADMIN)", description = "Get all account")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = AccountResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllAccount() {
        //    gọi service tìm tất cả account
        Iterable<Account> accounts = accountService.findAll();

        //kiểm tra kết quả empty  
        if (!accounts.iterator().hasNext()) {
            return new ResponseEntity<>("Account list empty!", HttpStatus.NOT_FOUND);
        }

        // tạo account response
        List<AccountResponse> accountResponses = new ArrayList<>();
        for (Account account : accounts) {
            if (account == null) {
                logger.error("Encountered null account in iterable");
                continue;
            }
            accountResponses.add(new AccountResponse(account));
        }
        
        return new ResponseEntity<>(accountResponses, HttpStatus.OK);
        
    }
    
    @Operation(summary = "Get account by account id (ADMIN)", description = "Get account by account id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = AccountResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByID")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAccountByID(@RequestParam Integer accountID) {
        Optional<Account> account = accountService.findById(accountID);
        
        if (!account.isPresent()) {
            return new ResponseEntity<>(new MessageResponse(("Cannot find account with account id{" + accountID + "}")), HttpStatus.NOT_FOUND);
        }
        
        AccountResponse accountResponse = new AccountResponse(account.get());
        
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }
    
    @Operation(summary = "Update account by account id", description = "Update account ")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = AccountResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping(value = "/update")
    @PreAuthorize("hasRole('ADMIN') ")
    public ResponseEntity<?> updateAccount(@Valid @RequestBody UpdateAccountRequest accountRequest) {
        // Check exist account
        Optional<Account> account = accountService.findById(accountRequest.getAccountID());
        
        if (!account.isPresent()) {
            return new ResponseEntity<>(new MessageResponse(("Cannot find account with account id{" + accountRequest.getAccountID() + "}")),
                    HttpStatus.NOT_FOUND);
        }
        // update account attribute
        account.get().updateAccount(accountRequest);

        // Store to database
        Account accountSave = accountService.save(account.get());

        // check resutl
        if (accountSave == null) {
            return new ResponseEntity<>(new MessageResponse("Failed to update account"), HttpStatus.INTERNAL_SERVER_ERROR);
            
        }

        // create account response
        AccountResponse accountResponse = new AccountResponse(accountSave);
        
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }
    
}
