/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.ActivityLevel;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.DietaryPreference;
import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.MemberBodyMass;
import com.fu.bmi_tracker.model.entities.Menu;
import com.fu.bmi_tracker.model.enums.EMealType;
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
import com.fu.bmi_tracker.services.MenuFoodService;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    MenuFoodService menuFoodService;

    @Autowired
    BMIUtils bMIUtils;

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

        // Kiểm tra Account ID nếu tồn tại Member retunr badRequest
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

        LocalDateTime now = LocalDateTime.now(ZoneId.of("GMT+7"));
        // Save member  Bổ sung menuID
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

    @Operation(
            summary = "Retrieve All Food in menu by meal type (MEMBER)",
            description = "Member send meal type to get food list")
    @ApiResponses({
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = DietaryPreference.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByMealType")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getMemberMenuByMealType(@RequestParam EMealType mealType) {
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // Find member by accountID
        Optional<Member> member = memberService.findByAccountID(principal.getId());
        System.out.println("AccoutnID: " + principal.getId());
        System.out.println("member: " + memberService.existsByAccountID(principal.getId()));
        if (!member.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Member already exists!"));
        }

        // call service find food
        List<Food> foods = menuFoodService.findFoodByMenu_MenuIDAndMealType(member.get().getMenuID(), mealType);

        //check food list 
        if (foods.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @Operation(
            summary = "Assign menu for member (ADVISOR)",
            description = "Create new menu with form")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Menu.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping(value = "/assignMenu")
    @PreAuthorize("hasRole('AVISOR')")
    public ResponseEntity<?> assignMenu(@Valid @RequestParam Integer menuID, Integer memberID) {
        // find member by member id
        Optional<Member> member = memberService.findById(memberID);
        // check existed
        if (!member.isPresent()) {
            return new ResponseEntity<>(new MessageResponse("Cannot find member with id {" + memberID + "}"), HttpStatus.BAD_REQUEST);
        }
        // set menuID
        member.get().setMenuID(menuID);
        //Update member
        memberService.save(member.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
