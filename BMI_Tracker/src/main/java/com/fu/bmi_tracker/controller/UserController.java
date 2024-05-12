/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.DietaryPreference;
import com.fu.bmi_tracker.model.entities.MealLog;
import com.fu.bmi_tracker.model.entities.User;
import com.fu.bmi_tracker.model.entities.UserBodyMass;
import com.fu.bmi_tracker.model.enums.EGender;
import com.fu.bmi_tracker.payload.request.CreateUserRequest;
import com.fu.bmi_tracker.payload.request.CreateMealLogRequest;
import com.fu.bmi_tracker.payload.response.CreateUserResponse;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.services.MealLogService;
import com.fu.bmi_tracker.services.UserBodyMassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fu.bmi_tracker.services.UserService;
import com.fu.bmi_tracker.util.BMIUtils;
import java.time.Instant;
import java.time.LocalDate;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "User", description = "User management APIs")
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    MealLogService mealLogService;

    @Autowired
    UserBodyMassService userBodyMassService;

    @Autowired
    BMIUtils bMIUtils;

//
//    @Operation(
//            summary = "Create new meal log with form",
//            description = "Create new meal log")
//    @ApiResponses({
//        @ApiResponse(responseCode = "201", content = {
//            @Content(schema = @Schema(implementation = MealLog.class), mediaType = "application/json")}),
//        @ApiResponse(responseCode = "403", content = {
//            @Content(schema = @Schema())}),
//        @ApiResponse(responseCode = "500", content = {
//            @Content(schema = @Schema())})})
//    @PostMapping(value = "/createNew")
//    public ResponseEntity<?> createNewMealLog(@Valid @RequestBody CreateMealLogRequest createMealLogRequest) {
//        // Kiểm tra user ID
//        // kiểm tra foodID
//        // Tạo mới MealLog
//        // Trả về kết quả
//        MealLog mealLog = new MealLog(createMealLogRequest);
//
//        MealLog mealLogSaved = mealLogService.save(mealLog);
//
//        return new ResponseEntity<>(mealLogSaved, HttpStatus.CREATED);
//    }
//
//    @Operation(
//            summary = "Get Meal Log",
//            description = "Retrieve all meal log of user from userID")
//    @ApiResponses({
//        @ApiResponse(responseCode = "201", content = {
//            @Content(schema = @Schema(implementation = MealLog.class), mediaType = "application/json")}),
//        @ApiResponse(responseCode = "403", content = {
//            @Content(schema = @Schema())}),
//        @ApiResponse(responseCode = "500", content = {
//            @Content(schema = @Schema())})})
//    @GetMapping(value = "/getMealLog/{userID}")
//    public ResponseEntity<?> getMealLogByUserID(@PathVariable("userID") Integer userID) {
//        // Tìm meal log bởi user id => sử dụng userID hay userID
//        List<MealLog> mealLogs;
//        return new ResponseEntity<>("", HttpStatus.CREATED);
//    }
//
    @Operation(
            summary = "Create new user information",
            description = "Activity Level"
            + " Little to no exercise:1.2"
            + " Light exercise :1.375 "
            + "Moderate exercise:1.55 Hard exercise:1725"
            + "Very hard exercise:1.9")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = CreateUserResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Kiểm tra Account ID 
        if (userService.existsByAccountID(principal.getId())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User already exists!"));
        }
        // tính BMI
        double bmi = bMIUtils.calculateBMI(createUserRequest.getWeight(), createUserRequest.getHeight());

        // tính BMR 
        int age = LocalDate.now().getYear() - principal.getBirthday().getYear();
        double bmr = bMIUtils.calculateBMR(createUserRequest.getWeight(),
                createUserRequest.getHeight(), age, EGender.Other);

        // tính tdee 
        double tdee = bMIUtils.calculateTDEE(bmr, 1.2);

        // calculateDefault default Calories
        int defaultCalories = bMIUtils.calculateDefaultCalories(tdee, createUserRequest.getTargetWeight());

        // Save user
        User user = new User(principal.getId(), createUserRequest.getGoalID(),
                createUserRequest.getTargetWeight(),
                tdee,
                bmr,
                defaultCalories,
                false,
                Instant.now(),
                createUserRequest.getDietaryPreferenceID(),
                createUserRequest.getActivityLevelID());

        User userSaved = userService.save(user);

        // Save user body mass
        UserBodyMass bodyMass = new UserBodyMass(createUserRequest.getHeight(),
                createUserRequest.getWeight(),
                age, bmi,
                Instant.now(), userSaved);

        userBodyMassService.save(bodyMass);

        //Generate suggestion menu
        CreateUserResponse createUserResponse = new CreateUserResponse(
                principal.getId(),
                defaultCalories,
                createUserRequest.getHeight(),
                createUserRequest.getWeight(), bmi
        );

        return new ResponseEntity<>(createUserResponse, HttpStatus.CREATED);

    }
}
