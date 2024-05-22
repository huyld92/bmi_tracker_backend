/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.ActivityLevel;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.MemberBodyMass;
import com.fu.bmi_tracker.payload.request.CreateMemberRequest;
import com.fu.bmi_tracker.payload.response.CreateMemberResponse;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.services.ActivityLevelService;
import com.fu.bmi_tracker.services.MealLogService;
import com.fu.bmi_tracker.services.MemberBodyMassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fu.bmi_tracker.util.BMIUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.security.core.context.SecurityContextHolder;
import com.fu.bmi_tracker.services.MemberService;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Member", description = "Member management APIs")
@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    MealLogService mealLogService;

    @Autowired
    MemberBodyMassService memberBodyMassService;

    @Autowired
    ActivityLevelService activityLevelService;

    @Autowired
    BMIUtils bMIUtils;

    //
    // @Operation(
    // summary = "Create new meal log with form",
    // description = "Create new meal log")
    // @ApiResponses({
    // @ApiResponse(responseCode = "201", content = {
    // @Content(schema = @Schema(implementation = MealLog.class), mediaType =
    // "application/json")}),
    // @ApiResponse(responseCode = "403", content = {
    // @Content(schema = @Schema())}),
    // @ApiResponse(responseCode = "500", content = {
    // @Content(schema = @Schema())})})
    // @PostMapping(value = "/createNew")
    // public ResponseEntity<?> createNewMealLog(@Valid @RequestBody
    // CreateMealLogRequest createMealLogRequest) {
    // // Kiểm tra member ID
    // // kiểm tra foodID
    // // Tạo mới MealLog
    // // Trả về kết quả
    // MealLog mealLog = new MealLog(createMealLogRequest);
    //
    // MealLog mealLogSaved = mealLogService.save(mealLog);
    //
    // return new ResponseEntity<>(mealLogSaved, HttpStatus.CREATED);
    // }
    //
    // @Operation(
    // summary = "Get Meal Log",
    // description = "Retrieve all meal log of member from memberID")
    // @ApiResponses({
    // @ApiResponse(responseCode = "201", content = {
    // @Content(schema = @Schema(implementation = MealLog.class), mediaType =
    // "application/json")}),
    // @ApiResponse(responseCode = "403", content = {
    // @Content(schema = @Schema())}),
    // @ApiResponse(responseCode = "500", content = {
    // @Content(schema = @Schema())})})
    // @GetMapping(value = "/getMealLog/{memberID}")
    // public ResponseEntity<?> getMealLogByMemberID(@PathVariable("memberID") Integer
    // memberID) {
    // // Tìm meal log bởi member id => sử dụng memberID hay memberID
    // List<MealLog> mealLogs;
    // return new ResponseEntity<>("", HttpStatus.CREATED);
    // }
    //
    @Operation(summary = "Create new member information", description = "Activity Level"
            + " Little to no exercise:1.2"
            + " Light exercise :1.375 "
            + "Moderate exercise:1.55 Hard exercise:1725"
            + "Very hard exercise:1.9")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = CreateMemberResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    public ResponseEntity<?> createNewMember(@Valid @RequestBody CreateMemberRequest createMemberRequest) {
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // Kiểm tra Account ID
        if (memberService.existsByAccountID(principal.getId())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Member already exists!"));
        }
        // tính BMI
        double bmi = bMIUtils.calculateBMI(createMemberRequest.getWeight(), createMemberRequest.getHeight());

        // tính BMR
        int age = LocalDate.now().getYear() - principal.getBirthday().getYear();
        double bmr = bMIUtils.calculateBMR(createMemberRequest.getWeight(),
                createMemberRequest.getHeight(), age, principal.getGender());

        // tính tdee
        // find activity level
        ActivityLevel activityLevel = activityLevelService.findById(createMemberRequest.getActivityLevelID()).get();
        double tdee = bMIUtils.calculateTDEE(bmr, activityLevel.getActivityLevel());

        // calculateDefault default Calories
        int defaultCalories = bMIUtils.calculateDefaultCalories(tdee, createMemberRequest.getTargetWeight());

        LocalDateTime now = LocalDateTime.now();
        // Save member
        Member member = new Member(principal.getId(),
                createMemberRequest.getTargetWeight(),
                tdee,
                bmr,
                defaultCalories,
                false,
                now,
                createMemberRequest.getDietaryPreferenceID(),
                new ActivityLevel(createMemberRequest.getActivityLevelID()));

        Member memberSaved = memberService.save(member);

        // Save member body mass
        MemberBodyMass bodyMass = new MemberBodyMass(createMemberRequest.getHeight(),
                createMemberRequest.getWeight(),
                age, bmi,
                now, memberSaved);

        memberBodyMassService.save(bodyMass);

        // Generate suggestion menu
        CreateMemberResponse createMemberResponse = new CreateMemberResponse(
                principal.getId(),
                defaultCalories,
                createMemberRequest.getHeight(),
                createMemberRequest.getWeight(), bmi);

        return new ResponseEntity<>(createMemberResponse, HttpStatus.CREATED);

    }
}
