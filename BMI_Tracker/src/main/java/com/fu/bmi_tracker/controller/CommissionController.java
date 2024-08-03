/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Commission;
import com.fu.bmi_tracker.model.entities.CommissionAllocation;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.payload.request.UpdateCommissionRequest;
import com.fu.bmi_tracker.payload.response.CommissionAdvisorResponse;
import com.fu.bmi_tracker.payload.response.CommissionAllocationResponse;
import com.fu.bmi_tracker.services.CommissionAllocationService;
import com.fu.bmi_tracker.services.CommissionService;
import com.fu.bmi_tracker.util.CommissionRateUtils;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    CommissionAllocationService commissionAllocationService;

    @Autowired
    private CommissionRateUtils commissionRateUtils;

    @Operation(summary = "Get all commission", description = " ")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CommissionAdvisorResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAll() {

        // Gọi Commission servicce lấy danh sách Commission của advisor
        Iterable<Commission> commissions = commissionService.findAll();

        // check result
        if (!commissions.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<CommissionAdvisorResponse> commissionResponses = new ArrayList<>();

        commissions.forEach(commission -> {
            commissionResponses.add(new CommissionAdvisorResponse(commission));
        });

        return new ResponseEntity<>(commissionResponses, HttpStatus.OK);
    }

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
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
            @Content(schema = @Schema(implementation = CommissionAdvisorResponse.class), mediaType = "application/json")}),
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
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // taoj commission response
        List<CommissionAdvisorResponse> commissionResponses = new ArrayList<>();

        commissions.forEach(commission -> {
            commissionResponses.add(new CommissionAdvisorResponse(commission));
        });
        return new ResponseEntity<>(commissionResponses, HttpStatus.OK);
    }

    @Operation(summary = "Get all commission by advisorID", description = "Send advisor ID and get all commisssion")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CommissionAdvisorResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping(value = "/update-paid")
    public ResponseEntity<?> updateCommission(@RequestBody UpdateCommissionRequest commissionRequest) {
        // Gọi Commission servicce update commission
        Commission commission = commissionService.updateCommission(commissionRequest);

        CommissionAdvisorResponse commissionResponses = new CommissionAdvisorResponse(commission);

        return new ResponseEntity<>(commissionResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get Commision Rate",
            description = "Get commision rate")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Float.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getCommisionRate")
    public ResponseEntity<?> getCommissionRate() {
        commissionRateUtils = new CommissionRateUtils();
        float commissionRate = commissionRateUtils.getCommissionRate();

        if (commissionRate != 0) {
            return new ResponseEntity<>(commissionRate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Get Commision Rate",
            description = "Get commision rate")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping("/configRate/{rate}")
    public ResponseEntity<?> updateCommissionRate(@PathVariable("rate") float rate) {

        commissionRateUtils.updateCommissionRate(rate);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get all commission by commission ID", description = "Send commission ID and get all commisssion")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CommissionAllocationResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/get-details")
    public ResponseEntity<?> getCommissionDetails(@RequestParam Integer commissionID) {
        // Gọi Commission servicce lấy danh sách Commission của advisor
        Iterable<CommissionAllocation> commissionAllocations = commissionAllocationService.getAllByCommissionID(commissionID);

        // check result
        if (!commissionAllocations.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // taoj commission response
        List<CommissionAllocationResponse> commissionResponses = new ArrayList<>();

        commissionAllocations.forEach(commissionAllocation -> {
            commissionResponses.add(new CommissionAllocationResponse(
                    commissionAllocation,
                    commissionAllocation.getSubscription().getSubscriptionNumber()));
        });
        return new ResponseEntity<>(commissionResponses, HttpStatus.OK);
    }
}
