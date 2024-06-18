/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.exceptions.ErrorMessage;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.MemberBodyMass;
import com.fu.bmi_tracker.payload.response.MemberBodyMassResponse;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
            summary = "Create member body mass (MEMBER)")
    @ApiResponses({
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = MemberBodyMass.class), mediaType = "application/json")}),
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
            summary = "Get all bodymass of member (MEMBER)")
    @ApiResponses({
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = MemberBodyMassResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "401", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/member/getAll")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getAllBodyMassOfMember() {
        // tìm account ID từ context
        CustomAccountDetailsImpl princal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // find member bằng accoundID
        Optional<Member> member = memberService.findByAccountID(princal.getId());
        if (!member.isPresent()) {
            return new ResponseEntity<>(new MessageResponse("Cannot find member!"), HttpStatus.NOT_FOUND);
        }

        Iterable<MemberBodyMass> bodyMasses = bodyMassService.findAllByAccountID(princal.getId());

        // tạo member body mass response
        List<MemberBodyMassResponse> bodyMassResponses = new ArrayList<>();
        bodyMasses.forEach(bodyMass -> bodyMassResponses.add(new MemberBodyMassResponse(bodyMass)));

        return ResponseEntity.ok(bodyMasses);
    }

    @Operation(
            summary = "Get all bodymass of member in 3 months (MEMBER)")
    @ApiResponses({
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = MemberBodyMassResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "401", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/member/getInMonth")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getAllBodyMassOfMemberIn3Month(@RequestParam(required = true) String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate;
        // Validation date 
        try {
            localDate = LocalDate.parse(date, formatter);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(
                    HttpStatus.BAD_REQUEST.value(),
                    new Date(), "Invalid date format. Please provide the date in the format yyyy-MM-dd", "");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        // tìm account ID từ context
        CustomAccountDetailsImpl princal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // tìm tất cả 
        Iterable<MemberBodyMass> bodyMasses = bodyMassService.findAllWithMonth(princal.getId(), localDate);

        // tạo member body mass response
        List<MemberBodyMassResponse> bodyMassResponses = new ArrayList<>();
        bodyMasses.forEach(bodyMass -> bodyMassResponses.add(new MemberBodyMassResponse(bodyMass)));

        return ResponseEntity.ok(bodyMassResponses);
    }
}
