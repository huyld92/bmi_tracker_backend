/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.exceptions.ErrorMessage;
import com.fu.bmi_tracker.payload.response.AdvisorBookingSummary;
import com.fu.bmi_tracker.payload.response.AdvisorCommissionSummary;
import com.fu.bmi_tracker.payload.response.AdvisorSummaryMenuWorkout;
import com.fu.bmi_tracker.payload.response.MemberBmiResponse;
import com.fu.bmi_tracker.services.AdvisorService;
import com.fu.bmi_tracker.services.BookingService;
import com.fu.bmi_tracker.services.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
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
@Tag(name = "Statistics", description = "Advisor management APIs")
@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    @Autowired
    AdvisorService advisorService;

    @Autowired
    BookingService bookingService;

    @Autowired
    MemberService memberService;

    @Operation(summary = "Statistics all menu, workout of advisor", description = "Receive advisor information and the total number of menus and the total number of workouts")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = AdvisorSummaryMenuWorkout.class))}),
        @ApiResponse(responseCode = "204", description = "There are no Advisors", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/advisor-total-menu-workout")
    public ResponseEntity<?> getStatisticsMenuWorkoutOfAdvisor() {

        // gọi service lấy tất cả advisor và Total menu, workout
        List<AdvisorSummaryMenuWorkout> statisticsResponse = advisorService.getAdvisorMenuWorkoutSummary();

        if (statisticsResponse.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(statisticsResponse, HttpStatus.OK);
    }

    @Operation(summary = "Statistics booking of advisor in month", description = "Pass the month and receive advisor information and the total number of bookings")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = AdvisorBookingSummary.class))}),
        @ApiResponse(responseCode = "204", description = "There are no Advisors", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/advisor-booking-summary")
    public ResponseEntity<?> getTotalBookingInMonth(@RequestParam(required = true, defaultValue = "yyyy-MM") String date) {
        // convert từ string date sang LocalDate format yyyy-MM
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate;
        try {
            localDate = LocalDate.parse((date + "-01"), formatter);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
                    new Date(), "Invalid date format. Please provide the date in the format yyyy-MM.", "");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        List<AdvisorBookingSummary> advisorBookingSummarys = bookingService.getAdvisorBookingSummaryByMonth(localDate);

        if (advisorBookingSummarys.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }

        return new ResponseEntity<>(advisorBookingSummarys, HttpStatus.OK);

    }

    @Operation(summary = "Statistics commission of advisor in month", description = "Pass the month and receive advisor information and commission")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = AdvisorCommissionSummary.class))}),
        @ApiResponse(responseCode = "204", description = "There are no Advisors", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/advisor-commission-summary")
    public ResponseEntity<?> getAdvisorCommissionSummary(@RequestParam(required = true, defaultValue = "yyyy-MM") String date) {
        // convert từ string date sang LocalDate format yyyy-MM
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate;
        try {
            localDate = LocalDate.parse((date + "-01"), formatter);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
                    new Date(), "Invalid date format. Please provide the date in the format yyyy-MM.", "");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        // gọi service tìm thông tin AdvisorCommissionSumary
        List<AdvisorCommissionSummary> commissionSumarys = advisorService.getAdvisorCommissionSummary(localDate);

        if (commissionSumarys.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }

        return new ResponseEntity<>(commissionSumarys, HttpStatus.OK);

    }

    @Operation(summary = "Statistics height and weight of member", description = "")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MemberBmiResponse.class))}),
        @ApiResponse(responseCode = "204", description = "There are no Advisors", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/member-bmi-summary")
    public ResponseEntity<?> getMemberBMISummary() {
        // goij service lấy danh sách thông tin member
        List<MemberBmiResponse> memberBmiResponses = memberService.getMemberBMISummary();
        if (memberBmiResponses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
        return new ResponseEntity<>(memberBmiResponses, HttpStatus.OK);

    }

}
