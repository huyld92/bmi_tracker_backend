/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.exceptions.ErrorMessage;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.MealLog;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.enums.EMealType;
import com.fu.bmi_tracker.services.FoodService;
import com.fu.bmi_tracker.services.MealLogService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fu.bmi_tracker.services.MemberService;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "MealLog", description = "Meal Log management APIs")
@RestController
@RequestMapping("/api/meallog")
public class MealLogController {

    @Autowired
    MealLogService mealLogService;

    @Autowired
    MemberService memberService;

    @Autowired
    FoodService foodService;

    @Operation(summary = "Retrieve meal list (Member)", description = "Get list meal of date from account id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MealLog.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no Tags", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getAllByDate")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getAllMealByDate(
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

        // Get Meal log with date
        Iterable<MealLog> mealLogs = mealLogService.findAllByDateOfMealAndMember_MemberID(dateOfMeal, member.getMemberID());

        // check meal empty
        if (!mealLogs.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(mealLogs, HttpStatus.OK);
    }

    @Operation(summary = "Retrieve food list (MEMBER)", description = "Member retrieve food list of meal by mealtype and date")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Food.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no Tags", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getFoodOfMeal")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getFoodListByMealType(
            @RequestParam String date,
            @RequestParam EMealType mealType
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate dateOfMeal;
        // Validation date
        try {
            dateOfMeal = LocalDate.parse(date, formatter);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), "Invalid date format. Please provide the date in the format yyyy-MM-dd.", "");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Member member = memberService.findByAccountID(principal.getId()).get();

        // Get list food id 
        Iterable<Integer> foodIDs = mealLogService.findFoodIDByDateOfMealAndMemberIDAndMealType(dateOfMeal,
                member.getMemberID(), mealType);

        if (!foodIDs.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // Get food from list food id
        Iterable<Food> foods = foodService.findByFoodIDIn((List<Integer>) foodIDs);

        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @Operation(summary = "Retrieve total calories meal of date", description = "Get total calories meal of date from account id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MealLog.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no Tags", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getTotalCaloriesOfDay")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getTotalCaloriesOfDay(
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

        // Get Meal log with date
        Iterable<MealLog> mealLogs = mealLogService.findAllByDateOfMealAndMember_MemberID(dateOfMeal, member.getMemberID());

        // check meal empty
        if (!mealLogs.iterator().hasNext()) {
            return new ResponseEntity<>(0, HttpStatus.NO_CONTENT);
        }

        // Calculate calories
        int totalCalories = 0;
        for (MealLog mealLog : mealLogs) {
            totalCalories += mealLog.getCalories();
        }

        return new ResponseEntity<>(totalCalories, HttpStatus.OK);
    }

}
