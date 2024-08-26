/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.exceptions.ErrorMessage;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.DailyRecord;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.payload.response.DailyRecordResponse;
import com.fu.bmi_tracker.services.DailyRecordService;
import com.fu.bmi_tracker.services.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
@Tag(name = "DailyRecord", description = "Daily record management APIs")
@RestController
@RequestMapping("/api/dailyrecords")
public class DailyRecordController {

    @Autowired
    DailyRecordService dailyRecordService;

    @Autowired
    MemberService memberService;

    // Lấy danh sách daily record trong một tuần từ một ngày bất kỳ trong tuần đó 
    @Operation(summary = "Retrieve daily record of week (MEMBER)", description = "Get daily record for a week by providing the value of a day of the week to get")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = DailyRecordResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no meal", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getAllDailyRecordOfWeekByDate")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getAllDailyRecordOfWeekByDate(@RequestParam String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate;
        // Validation date 
        try {
            localDate = LocalDate.parse(date, formatter);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), "Invalid date format. Please provide the date in the format dd-MM-yyyy.", "");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        // Get Member id from acccount id context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberService.findByAccountID(principal.getId()).get();

        // Tìm danh sách daily record từ member ID và date trong vòng một tuần
        List<DailyRecord> dailyRecords = dailyRecordService.getDailyRecordsForWeek(member.getMemberID(), localDate);

        if (dailyRecords.isEmpty()) {
            // 204 không có 
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo response
        List<DailyRecordResponse> responses = new ArrayList<>();
        dailyRecords.forEach((DailyRecord dr) -> responses.add(new DailyRecordResponse(dr)));

        return new ResponseEntity<>(responses, HttpStatus.OK);

    } 
}
