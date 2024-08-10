/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.UserRequest;
import com.fu.bmi_tracker.payload.response.UserRequestResponse;
import com.fu.bmi_tracker.services.UserRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "UserRequest", description = "UserRequest management APIs")
@RestController
@RequestMapping("/api/user-requests")
public class UserRequestController {

    @Autowired
    UserRequestService userRequestService;

    @Operation(
            summary = "Get all userRequest for user",
            description = "Logged with account id to get all userRequest ")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = UserRequestResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "Response empty list"),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/get-all-for-user")
    @PreAuthorize("hasRole('ADMIN')or hasRole('MEMBER')or hasRole('MANAGER')or hasRole('ADVISOR')")
    public ResponseEntity<?> getAllForUser() {
        // lấy id account hiện tại trong context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        Iterable<UserRequest> userRequests = userRequestService.findByAccountID(principal.getId());

        if (!userRequests.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo menu response
        List<UserRequestResponse> userRequestResponses = new ArrayList<>();

        userRequests.forEach(userRequest -> {
            userRequestResponses.add(new UserRequestResponse(userRequest));
        });

        return new ResponseEntity<>(userRequestResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all user request")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = UserRequestResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/get-all")
    public ResponseEntity<?> getAll() {
        // tìm notificaion
        Iterable<UserRequest> userRequests = userRequestService.findAll();

        if (!userRequests.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }

        // tạo menu response
        List<UserRequestResponse> userRequestResponses = new ArrayList<>();

        userRequests.forEach(userRequest -> {
            userRequestResponses.add(new UserRequestResponse(userRequest));
        });
        return new ResponseEntity<>(userRequestResponses, HttpStatus.OK);

    }

}
