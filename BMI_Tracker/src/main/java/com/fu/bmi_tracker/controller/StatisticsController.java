/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.exceptions.ErrorMessage;
import com.fu.bmi_tracker.model.entities.DailyRecord;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.MemberBodyMass;
import com.fu.bmi_tracker.payload.response.CommissionSummaryResponse;
import com.fu.bmi_tracker.payload.response.CountMenuResponse;
import com.fu.bmi_tracker.payload.response.CountSubscriptionResponse;
import com.fu.bmi_tracker.payload.response.CountWorkoutResponse;
import com.fu.bmi_tracker.payload.response.DailyRecordResponse;
import com.fu.bmi_tracker.payload.response.MemberBodyMassResponse;
import com.fu.bmi_tracker.payload.response.TotalAdvisorMemberResponse;
import com.fu.bmi_tracker.services.AdvisorService;
import com.fu.bmi_tracker.services.CommissionService;
import com.fu.bmi_tracker.services.DailyRecordService;
import com.fu.bmi_tracker.services.MemberBodyMassService;
import com.fu.bmi_tracker.services.MemberService;
import com.fu.bmi_tracker.services.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fu.bmi_tracker.services.SubscriptionService;
import com.fu.bmi_tracker.services.WorkoutService;
import com.fu.bmi_tracker.util.BMIUtils;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.web.bind.annotation.RequestParam;

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
    SubscriptionService subscriptionService;

    @Autowired
    MemberService memberService;

    @Autowired
    MenuService menuService;

    @Autowired
    WorkoutService workoutService;

    @Autowired
    CommissionService commissionService;

    @Autowired
    DailyRecordService dailyRecordService;

    @Autowired
    MemberBodyMassService memberBodyMassService;
//
//    @Operation(summary = "Statistics all menu, workout of advisor", description = "Receive advisor information and the total number of menus and the total number of workouts")
//    @ApiResponses({
//        @ApiResponse(responseCode = "200", content = {
//            @Content(schema = @Schema(implementation = AdvisorSummaryMenuWorkout.class))}),
//        @ApiResponse(responseCode = "204", description = "There are no Advisors", content = {
//            @Content(schema = @Schema())}),
//        @ApiResponse(responseCode = "500", content = {
//            @Content(schema = @Schema())})})
//    @GetMapping("/advisor-total-menu-workout")
//    public ResponseEntity<?> getStatisticsMenuWorkoutOfAdvisor() {
//
//        // gọi service lấy tất cả advisor và Total menu, workout
//        List<AdvisorSummaryMenuWorkout> statisticsResponse = advisorService.getAdvisorMenuWorkoutSummary();
//
//        if (statisticsResponse.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//
//        return new ResponseEntity<>(statisticsResponse, HttpStatus.OK);
//    }

//    @Operation(summary = "Statistics booking of advisor in 6 month", description = "")
//    @ApiResponses({
//        @ApiResponse(responseCode = "200", content = {
//            @Content(schema = @Schema(implementation = AdvisorSubscriptionSummary.class))}),
//        @ApiResponse(responseCode = "204", description = "There are no Advisors", content = {
//            @Content(schema = @Schema())}),
//        @ApiResponse(responseCode = "500", content = {
//            @Content(schema = @Schema())})})
//    @GetMapping("/advisor-booking-summary")
//    public ResponseEntity<?> getTotalBookingIn6MonthOfAdvisor() {
//
//        List<AdvisorSubscriptionSummary> advisorBookingSummarys = subscriptionService.getAdvisorSubscriptionSummaryByMonth();
//
//        if (advisorBookingSummarys.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//
//        }
//
//        return new ResponseEntity<>(advisorBookingSummarys, HttpStatus.OK);
//    }
//    @Operation(summary = "Statistics commission of advisor in month", description = "Pass the month and receive advisor information and commission")
//    @ApiResponses({
//        @ApiResponse(responseCode = "200", content = {
//            @Content(schema = @Schema(implementation = AdvisorCommissionSummary.class))}),
//        @ApiResponse(responseCode = "204", description = "There are no Advisors", content = {
//            @Content(schema = @Schema())}),
//        @ApiResponse(responseCode = "500", content = {
//            @Content(schema = @Schema())})})
//    @GetMapping("/advisor-commission-summary")
//    public ResponseEntity<?> getAdvisorCommissionSummary() {
//        // gọi service tìm thông tin AdvisorCommissionSumary
//        List<AdvisorCommissionSummary> commissionSumarys = advisorService.getAdvisorCommissionSummary();
//
//        if (commissionSumarys.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//
//        }
//
//        return new ResponseEntity<>(commissionSumarys, HttpStatus.OK);
//
//    }
//    @Operation(summary = "Statistics height and weight of member", description = "")
//    @ApiResponses({
//        @ApiResponse(responseCode = "200", content = {
//            @Content(schema = @Schema(implementation = MemberBmiResponse.class))}),
//        @ApiResponse(responseCode = "204", description = "There are no Advisors", content = {
//            @Content(schema = @Schema())}),
//        @ApiResponse(responseCode = "500", content = {
//            @Content(schema = @Schema())})})
//    @GetMapping("/member-bmi-summary")
//    public ResponseEntity<?> getMemberBMISummary() {
//        // goij service lấy danh sách thông tin member
//        List<MemberBmiResponse> memberBmiResponses = memberService.getMemberBMISummary();
//        if (memberBmiResponses.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity<>(memberBmiResponses, HttpStatus.OK);
//
//    }
    @Operation(summary = "Statistics total commission in 6 months", description = "")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CommissionSummaryResponse.class))}),
        @ApiResponse(responseCode = "204", description = "There are no Advisors", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/commission-summary")
    public ResponseEntity<?> getCommissionSummaryIn6Months() {
        // goij service tìm tổng số commission trong vòng 6 tháng
        List<CommissionSummaryResponse> commissionSummaryResponses = commissionService.getCommissionSummaryIn6Months();
        if (commissionSummaryResponses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(commissionSummaryResponses, HttpStatus.OK);
    }

    @Operation(summary = "Statistics total menu in 6 months", description = "")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CountMenuResponse.class))}),
        @ApiResponse(responseCode = "204", description = "There are no Advisors", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/get-total-menu")
    public ResponseEntity<?> getTotalMenu() {
        // gọi service lấy tổng số menu
        List<CountMenuResponse> countMenuResponses = menuService.countTotalMenuIn6Months();

        if (countMenuResponses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(countMenuResponses, HttpStatus.OK);
    }

    @Operation(summary = "Statistics total menu in 6 months", description = "")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CountWorkoutResponse.class))}),
        @ApiResponse(responseCode = "204", description = "There are no Advisors", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/get-total-workout")
    public ResponseEntity<?> getTotalWorkout() {
        // gọi service lấy tổng số workout
        List<CountWorkoutResponse> countWorkoutResponses = workoutService.countTotalWorkoutIn6Months();

        if (countWorkoutResponses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(countWorkoutResponses, HttpStatus.OK);
    }

    @Operation(summary = "Statistics total subscription in 6 months", description = "")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CountSubscriptionResponse.class))}),
        @ApiResponse(responseCode = "204", description = "There are no Advisors", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/get-total-subscription")
    public ResponseEntity<?> getTotalsubscription() {
        // gọi service lấy tổng số booking
        List<CountSubscriptionResponse> countSubscriptionResponses = subscriptionService.countTotalSubscriptionIn6Months();

        if (countSubscriptionResponses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(countSubscriptionResponses, HttpStatus.OK);
    }

    @Operation(summary = "Statistics total advisor and member", description = "")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = TotalAdvisorMemberResponse.class))}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/get-total-advisor-member")
    public ResponseEntity<?> getTotalAdvisorMember() {
        // gọi advisor service count total advisor
        Long totalAdvisor = advisorService.countTotalAdvisor();
        // gọi member service count total member
        Long totalMember = memberService.countTotalMember();

        // tạo response
        TotalAdvisorMemberResponse response = new TotalAdvisorMemberResponse(
                totalAdvisor,
                totalMember);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // từ hôm nay lấy ngược 7 ngày trước cho dailyrecord của member
    @Operation(summary = "Statistics dailyrecord of member in 7 days", description = "")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = TotalAdvisorMemberResponse.class))}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("daily-record/week-by-date")
    public ResponseEntity<?> getDailyRecordIn7Days(@RequestParam Integer memberID, @RequestParam String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate;
        // Validation date 
        try {
            localDate = LocalDate.parse(date, formatter);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), "Invalid date format. Please provide the date in the format yyyy-MM-dd.", "");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        // Gọi memberService tìm member bằng memberID
        Member member = memberService.findById(memberID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find member with id{" + memberID + "}!"));

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

    //lấy cân nặng trong 30days
    @Operation(
            summary = "Get all bodymass of member in 1 months ")
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
    @GetMapping("/member-bodymass/getInMonth")
    public ResponseEntity<?> getAllBodyMassOfMemberIn3Month(@RequestParam Integer memberID, @RequestParam(required = true) String date) {

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
        // tìm tất cả bodymass bawfng memberID
        Iterable<MemberBodyMass> bodyMasses = memberBodyMassService.getBodyMassInMonthByMemberID(memberID, localDate);

        // tạo member body mass response
        List<MemberBodyMassResponse> bodyMassResponses = new ArrayList<>();
        bodyMasses.forEach(bodyMass -> {
            BMIUtils bMIUtils = new BMIUtils();

            double bmi = bMIUtils.calculateBMI(bodyMass.getWeight(), bodyMass.getHeight());

            int age = LocalDate.now().getYear() - bodyMass.getMember().getAccount().getBirthday().getYear();

            bodyMassResponses.add(new MemberBodyMassResponse(bodyMass, age, bmi));
        }
        );
        return ResponseEntity.ok(bodyMassResponses);
    }
}
