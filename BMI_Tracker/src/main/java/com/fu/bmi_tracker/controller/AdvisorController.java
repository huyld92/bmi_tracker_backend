/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.payload.response.AdvisorAllResponse;
import com.fu.bmi_tracker.payload.response.AdvisorResponse;
import com.fu.bmi_tracker.services.AdvisorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
            @Content(schema = @Schema(implementation = AdvisorAllResponse.class))}),
        @ApiResponse(responseCode = "204", description = "There are no Advisors", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllAdvisors() {

        Iterable<Advisor> advisors = advisorService.findAll();

        if (!advisors.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo advisor response
        List<AdvisorAllResponse> advisorsResponses = new ArrayList<>();

        advisors.forEach(advisor -> {
            advisorsResponses.add(new AdvisorAllResponse(advisor));
        });

        return new ResponseEntity<>(advisorsResponses, HttpStatus.OK);

    }

    @Operation(summary = "Retrieve all Advisors (MEMBER)", description = "Member don't get isActive = false")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = AdvisorResponse.class))}),
        @ApiResponse(responseCode = "204", description = "There are no Advisors", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getWithDetails")
    public ResponseEntity<?> getAllAdvisorsWithDetails() {

        List<Advisor> advisors = advisorService.findAllAdvisorIsActive();

        if (advisors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo advisor response
        List<AdvisorResponse> advisorsResponses = new ArrayList<>();

        advisors.forEach(advisor -> {
            advisorsResponses.add(new AdvisorResponse(advisor));
        });

        return new ResponseEntity<>(advisorsResponses, HttpStatus.OK);

    }

    @Operation(summary = "Retrieve all Advisors")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = AdvisorResponse.class))}),
        @ApiResponse(responseCode = "204", description = "There are no Advisors", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getByID")
    public ResponseEntity<?> getAdvisorByID(@RequestParam Integer advisorID) {
        //tìm adivosr bằng adivsorID
        Optional<Advisor> advisor = advisorService.findById(advisorID);

        if (!advisor.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo AdvisorResposne
        AdvisorResponse advisorResponse = new AdvisorResponse(advisor.get());

        return new ResponseEntity<>(advisorResponse, HttpStatus.OK);

    }

}
