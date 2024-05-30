/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.payload.response.AdvisorResponse;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.services.AdvisorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Advisor", description = "Advisor management APIs")
@RestController
@RequestMapping("/api/advisors")
public class AdvisorController {

    @Autowired
    AdvisorService advisorService;

    @Operation(summary = "Retrieve all Advisors")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Advisor.class)) }),
            @ApiResponse(responseCode = "204", description = "There are no Advisors", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema()) }) })
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllAdvisors() {

        Iterable<Advisor> advisors = advisorService.findAll();

        if (!advisors.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(advisors, HttpStatus.OK);

    }
    
        @Operation(summary = "Retrieve all Advisors")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = AdvisorResponse.class))}),
        @ApiResponse(responseCode = "204", description = "There are no Advisors", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getWithDetails")
    public ResponseEntity<?> getAllAdvisorsWithDetails() {

        List<AdvisorResponse> advisors = advisorService.findAllAdvisorsWithDetails();

        if (advisors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(advisors, HttpStatus.OK);

    }

    @Operation(summary = "Retrieve all Advisors (ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Advisor.class)) }),
            @ApiResponse(responseCode = "204", description = "There are no Advisors", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema()) }) })
    @GetMapping("/getByID")
    public ResponseEntity<?> getAdvisorByID(@RequestParam Integer advisorID) {
        //
        Optional<Advisor> advisors = advisorService.findById(advisorID);

        if (!advisors.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(advisors, HttpStatus.OK);

    }

    @Operation(summary = "Update Advisors information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Advisor.class)) }),
            @ApiResponse(responseCode = "400", description = "There are no Advisors", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema()) }) })
    @PutMapping("/update")
    public ResponseEntity<?> updateAdvisor(@RequestParam Integer height, @RequestParam Integer weight) {
        // get account from context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // find Advisor
        Advisor advisor = advisorService.findByAccountID(principal.getId());

        if (advisor == null) {
            return new ResponseEntity<>(new MessageResponse("Cannot find advisor!"), HttpStatus.BAD_REQUEST);
        }

        // validate value request
        if (height > 30) {
            advisor.setHeight(height);
        }

        if (weight > 0) {
            advisor.setWeight(weight);
        }

        // store advisor
        advisorService.save(advisor);

        return new ResponseEntity<>(advisor, HttpStatus.OK);

    }
}
