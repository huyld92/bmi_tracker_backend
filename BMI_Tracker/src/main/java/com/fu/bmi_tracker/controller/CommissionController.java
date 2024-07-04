/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Commission;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.payload.response.CommissionAdvisorResponse;
import com.fu.bmi_tracker.services.CommissionService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Commission", description = "Commission management APIs")
@RestController
@RequestMapping("/api/commissions")
public class CommissionController {

    @Autowired
    CommissionService commissionService;

    @Operation(summary = "Get all commission by advisor (ADVISOR)", description = "Login with role advisor to get all commission")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CommissionAdvisorResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/advisor/getAll")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> getAllByAdvisor() {
        // Tìm account id từ context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // Gọi Commission servicce lấy danh sách Commission của advisor
        Iterable<Commission> commissions = commissionService.getByAdvisor(principal.getId());

        // check result
        if (!commissions.iterator().hasNext()) {
            return new ResponseEntity<>("Failed to get commissions of advisor", HttpStatus.INTERNAL_SERVER_ERROR);

        }
        List<CommissionAdvisorResponse> commissionResponses = new ArrayList<>();

        commissions.forEach(commission -> {
            commissionResponses.add(new CommissionAdvisorResponse(commission));
        });

        return new ResponseEntity<>(commissionResponses, HttpStatus.OK);
    }

    @Operation(summary = "Get all commission by advisorID", description = "Send advisor ID and get all commisssion")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Commission.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/advisor/getByID")
    public ResponseEntity<?> getAllByAdvisorID(@RequestParam Integer advisorID) {
        // Gọi Commission servicce lấy danh sách Commission của advisor
        Iterable<Commission> commissions = commissionService.getByAdvisorID(advisorID);

        // check result
        if (!commissions.iterator().hasNext()) {
            return new ResponseEntity<>("Failed to get commissions of advisor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<CommissionAdvisorResponse> commissionResponses = new ArrayList<>();

        commissions.forEach(commission -> {
            commissionResponses.add(new CommissionAdvisorResponse(commission));
        });

        return new ResponseEntity<>(commissionResponses, HttpStatus.OK);
    }
}
