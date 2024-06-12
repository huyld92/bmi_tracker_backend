/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.exceptions.ErrorMessage;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.DailyRecord;
import com.fu.bmi_tracker.model.entities.ActivityLog;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.payload.request.CreateActivityLogRequest;
import com.fu.bmi_tracker.services.DailyRecordService;
import com.fu.bmi_tracker.services.ActivityLogService;
import com.fu.bmi_tracker.services.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "ActivityLog", description = "Activity Log management APIs")
@RestController
@RequestMapping("/api/activitylog")
public class ActivityLogController {

    @Autowired
    ActivityLogService activityLogService;

    @Autowired
    MemberService memberService;

    @Autowired
    DailyRecordService dailyRecordService;

    @Operation(summary = "Retrieve activity log list (Member)", description = "Get list activity log of date")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ActivityLog.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no activity", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getAllByDate")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getAllActivityLogByDate(
            @RequestParam(required = true) String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfMeal;

        // Validation date 
        try {
            dateOfMeal = LocalDate.parse(date, formatter);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), "Invalid date format. Please provide the date in the format yyyy-MM-dd.", "");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        // Get Member id from acccount id context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberService.findByAccountID(principal.getId()).get();

        Optional<DailyRecord> dailyRecord = dailyRecordService.findByMemberIDAndDate(member.getMemberID(), dateOfMeal);

        // new chưa tồn tại record thì create new
        if (!dailyRecord.isPresent()) {
            dailyRecordService.save(new DailyRecord(dateOfMeal, member.getDefaultCalories(), member));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // Get all Meal log with accountid and date
        Iterable<ActivityLog> activityLogs = activityLogService.findByRecordID(dailyRecord.get().getRecordID());

        // check activity empty
        if (!activityLogs.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(activityLogs, HttpStatus.OK);
    }

    @Operation(
            summary = "Create new activity log (MEMBER)",
            description = "Create new activivty log")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = ActivityLog.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> createNewActivityLog(@Valid @RequestBody CreateActivityLogRequest activityLogRequest) {
        // Create new object
        ActivityLog activityLog = new ActivityLog(activityLogRequest);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfActivity;

        // Validation date 
        try {
            dateOfActivity = LocalDate.parse(activityLogRequest.getDateOfActivity(), formatter);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), "Invalid date format. Please provide the date in the format yyyy-MM-dd.", "");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        // Get Member from acccount id context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberService.findByAccountID(principal.getId()).get();

        Optional<DailyRecord> dailyRecord = dailyRecordService.findByMemberIDAndDate(member.getMemberID(), dateOfActivity);

        // new chưa tồn tại record thì create new
        if (!dailyRecord.isPresent()) {
            dailyRecord = Optional.of(dailyRecordService.save(new DailyRecord(dateOfActivity, member.getDefaultCalories(), member)));

        }

        //set record ID
        activityLog.setRecordID(dailyRecord.get().getRecordID());

        // Store to database
        ActivityLog activityLogSave = activityLogService.save(activityLog);

        // check result
        if (activityLogSave == null) {
            return new ResponseEntity<>("Failed to create new activityLog", HttpStatus.INTERNAL_SERVER_ERROR);

        }

        // update calories out of daily record
        int caloriesOut = dailyRecord.get().getTotalCaloriesOut() + activityLogSave.getCaloriesBurned();

        dailyRecord.get().setTotalCaloriesOut(caloriesOut);

        dailyRecordService.save(dailyRecord.get());

        return new ResponseEntity<>(activityLogSave, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update activity log (MEMBER)",
            description = "Create new activivty log")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = ActivityLog.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/update")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> updateActivityLog(@Valid @RequestBody CreateActivityLogRequest activityLogRequest) {
        // Create new object
        ActivityLog activityLog = new ActivityLog(activityLogRequest);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfActivity;

        // Validation date 
        try {
            dateOfActivity = LocalDate.parse(activityLogRequest.getDateOfActivity(), formatter);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), "Invalid date format. Please provide the date in the format yyyy-MM-dd.", "");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        // Get Member from acccount id context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberService.findByAccountID(principal.getId()).get();

        DailyRecord dailyRecord = dailyRecordService.findByMemberIDAndDate(member.getMemberID(), dateOfActivity).get();

        // new chưa tồn tại record thì create new
        if (dailyRecord == null) {
            dailyRecord = dailyRecordService.save(new DailyRecord(dateOfActivity, member.getDefaultCalories(), member));

        }

        //set record ID
        activityLog.setRecordID(dailyRecord.getRecordID());

        // Store to database
        ActivityLog activityLogSave = activityLogService.save(activityLog);

        // check result
        if (activityLogSave == null) {
            return new ResponseEntity<>("Failed to create new activityLog", HttpStatus.INTERNAL_SERVER_ERROR);

        }

        // update calories out of daily record
        int caloriesOut = dailyRecord.getTotalCaloriesOut() + activityLogSave.getCaloriesBurned();

        dailyRecord.setTotalCaloriesOut(caloriesOut);

        dailyRecordService.save(dailyRecord);

        return new ResponseEntity<>(activityLogSave, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete activity log (MEMBER)",
            description = "Delete activity Log by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> deleteActivityLogById(@RequestParam int activityLogID) {
        // Find activivty log by activivty logid
        Optional<ActivityLog> actityLog = activityLogService.findById(activityLogID);
        // check existed
        if (actityLog.isPresent()) {
            //find Daily record
            Optional<DailyRecord> dailyRecord = dailyRecordService.findById(actityLog.get().getRecordID());
            //Update coloriesIn
            int coloriesOut = dailyRecord.get().getTotalCaloriesOut() - actityLog.get().getCaloriesBurned();
            dailyRecord.get().setTotalCaloriesOut(coloriesOut);

            // delete activivty log
            activityLogService.deleteById(activityLogID);

            //update dailyrecord
            dailyRecordService.save(dailyRecord.get());
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
