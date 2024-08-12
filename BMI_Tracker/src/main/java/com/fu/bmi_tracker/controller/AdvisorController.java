/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.EmailDetails;
import com.fu.bmi_tracker.payload.response.AdvisorAllResponse;
import com.fu.bmi_tracker.payload.response.AdvisorResponse;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.services.AdvisorService;
import com.fu.bmi_tracker.services.EmailService;
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
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    EmailService emailService;

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

    @Operation(
            summary = "Activate advisor",
            description = "Activate advisor by advisorID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema(implementation = MessageResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping("/activate/{advisorID}")
    public ResponseEntity<?> activateAdvisor(@PathVariable("advisorID") int advisorID) {
        Optional<Advisor> advisor = advisorService.findById(advisorID);

        if (advisor.isPresent()) {
            if (advisor.get().getIsActive()) {
                return new ResponseEntity<>(new MessageResponse("Advisor was previously active."), HttpStatus.BAD_REQUEST);
            } else {
                advisor.get().setIsActive(Boolean.TRUE);
                advisorService.save(advisor.get());
                String msgBody = "Dear " + advisor.get().getAccount().getFullName() + ",\n\n"
                        + "We are pleased to inform you that your account has been successfully activated.\n"
                        + "You can now log in and start exploring all the features and services we offer.\n\n"
                        + "If you have any questions or need further assistance, feel free to contact our support team at [Support Email: huyddse63197@fpt.edu.vn].\n\n"
                        + "Thank you for choosing BMI.\n\n"
                        + "Best regards,\n\n"
                        + "BMI Support Team";
                EmailDetails details = new EmailDetails();
                details.setSubject("Your Account is Now Active!");
                details.setRecipient(advisor.get().getAccount().getEmail());
                details.setMsgBody(msgBody);

                emailService.sendSimpleMail(details);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse("Activate advisor failed"), HttpStatus.NOT_FOUND);
        }
    }
}
