/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.MemberBodyMass;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.services.MemberBodyMassService;
import com.fu.bmi_tracker.services.MemberService;
import com.fu.bmi_tracker.util.BMIUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Member Body Mass", description = "Member body mass management APIs")
@RestController
@RequestMapping("/api/bodymass")
public class MemberBodyMassController {

    @Autowired
    MemberBodyMassService bodyMassService;

    @Autowired
    MemberService memberService;

    @Autowired
    BMIUtils bMIUtils;

    @Operation(
            summary = "Create member body mass")
    @ApiResponses({
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "401", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping("/create")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> createNewBodyMass(@RequestParam Integer height, @RequestParam Integer weight) {
        CustomAccountDetailsImpl princal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // find member 
        Optional<Member> member = memberService.findByAccountID(princal.getId());
        if (!member.isPresent()) {
            return new ResponseEntity<>(new MessageResponse("Cannot find member!"), HttpStatus.NOT_FOUND);
        }
        LocalDateTime now = LocalDateTime.now(ZoneId.of("GMT+7"));

        int age = now.getYear() - princal.getBirthday().getYear();
        Double bmi = bMIUtils.calculateBMI(weight, height);

        // Save member body mass
        MemberBodyMass bodyMass = new MemberBodyMass(height,
                weight,
                age,
                bmi,
                now, member.get()
        );

        bodyMassService.save(bodyMass);

        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }

    @Operation(
            summary = "Get all bodymass of member")
    @ApiResponses({
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "401", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getAllByID")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getAllBodyMassByID() {
        CustomAccountDetailsImpl princal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // find member 
        Optional<Member> member = memberService.findByAccountID(princal.getId());
        if (!member.isPresent()) {
            return new ResponseEntity<>(new MessageResponse("Cannot find member!"), HttpStatus.NOT_FOUND);
        }

        Iterable<MemberBodyMass> bodyMasses = bodyMassService.findAllByAccountID(princal.getId());

        return ResponseEntity.ok(bodyMasses);
    }
}
