/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Account;
import com.fu.bmi_tracker.model.entities.EmailDetails;
import com.fu.bmi_tracker.model.entities.Role;
import com.fu.bmi_tracker.payload.request.CreateAccountRequest;
import com.fu.bmi_tracker.payload.request.UpdateAccountRequest;
import com.fu.bmi_tracker.payload.response.AccountResponse;
import com.fu.bmi_tracker.services.AccountService;
import com.fu.bmi_tracker.services.EmailService;
import com.fu.bmi_tracker.services.RoleService;
import com.fu.bmi_tracker.util.PasswordUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
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
    RoleService roleService;

    @Autowired
    PasswordUtils passwordUtils;

    @Autowired
    PasswordEncoder encoder;

    @Operation(summary = "Create new account (ADMIN)", description = "Create new account with role name (ROLE_ADMIN, ROLE_USER, ROLE_ADVISOR)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = Account.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema()) }) })
    @PostMapping(value = "/createNew")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        // Create new object
        Account account = new Account(createAccountRequest);

        // set Role
        Role accountRole = roleService.findByRoleName(createAccountRequest.getRole());
        account.setRole(accountRole);

        // Generate default password and endcode to save
        String defaultPassword = passwordUtils.generateRandomPassword(10);

        account.setPassword(encoder.encode(defaultPassword));

        // Store to database
        Account accountSave = accountService.save(account);

        // check result
        if (accountSave == null) {
            return new ResponseEntity<>("Failed to create new account", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        EmailDetails details = new EmailDetails(accountSave.getEmail(),
                "Your New Account Password",
                defaultPassword,
                accountSave.getFullName());
        emailService.sendSimpleMail(details);

        return new ResponseEntity<>(accountSave, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all account (ADMIN)", description = "Get all account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = AccountResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema()) }) })
    @GetMapping(value = "/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllAccount() {
        List<AccountResponse> accountResponse = accountService.findAllAccountResponse();

        if (accountResponse.isEmpty()) {
            return new ResponseEntity<>("Account list empty!", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get account by account id (ADMIN)", description = "Get account by account id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = AccountResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema()) }) })
    @GetMapping(value = "/getByID")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAccountByID(@RequestParam Integer accountID) {
        Optional<Account> account = accountService.findById(accountID);

        if (!account.isPresent()) {
            return new ResponseEntity<>("Cannot find account with account id{" + accountID + "}", HttpStatus.NOT_FOUND);
        }

        AccountResponse accountResponse = new AccountResponse(account.get());

        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }

    @Operation(summary = "Update account by account id", description = "Update account ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Account.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema()) }) })
    @PutMapping(value = "/update")
    @PreAuthorize("hasRole('ADMIN') ")
    public ResponseEntity<?> updateAccount(@Valid @RequestBody UpdateAccountRequest accountRequest) {
        // Check exist account
        Optional<Account> account = accountService.findById(accountRequest.getAccountID());

        if (!account.isPresent()) {
            return new ResponseEntity<>("Cannot find account with id{" + accountRequest.getAccountID() + "}",
                    HttpStatus.NOT_FOUND);
        }
        // update account attribute
        account.get().updateAccount(accountRequest);

        // Store to database
        Account accountSave = accountService.save(account.get());

        // check resutl
        if (accountSave == null) {
            return new ResponseEntity<>("Failed to update account", HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(accountSave, HttpStatus.OK);
    }

}
