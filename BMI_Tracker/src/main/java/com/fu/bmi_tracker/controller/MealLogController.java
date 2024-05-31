/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.exceptions.ErrorMessage;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.DailyRecord;
import com.fu.bmi_tracker.model.entities.MealLog;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.enums.EMealType;
import com.fu.bmi_tracker.payload.request.CreateMealLogRequest;
import com.fu.bmi_tracker.payload.response.MealWithCaloriesResponse;
import com.fu.bmi_tracker.services.DailyRecordService;
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
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    DailyRecordService dailyRecordService;

    @Operation(summary = "Retrieve meal log list (Member)", description = "Get list meal log of date")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MealLog.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no meal", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getAllByDate")
    @PreAuthorize("hasRole('MEMBER')")
    @SuppressWarnings("UnusedAssignment")
    public ResponseEntity<?> getAllMealLogByDate(
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
        Iterable<MealLog> mealLogs = mealLogService.findByRecordID(dailyRecord.get().getRecordID());

        // check meal empty
        if (!mealLogs.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(mealLogs, HttpStatus.OK);
    }

    @Operation(summary = "Retrieve total calories meal of date", description = "Get total calories meal of date from account id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MealLog.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no meal", content = {
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

        //Find record ID by memberID and Date
        Optional<DailyRecord> record = dailyRecordService.findByMemberIDAndDate(member.getMemberID(), dateOfMeal);

        return new ResponseEntity<>(record.get().getTotalCaloriesIn(), HttpStatus.OK);
    }

    @Operation(
            summary = "Create new mealL log (MEMBER)",
            description = "Create new meal log")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = MealLog.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> createNewMealLog(@Valid @RequestBody CreateMealLogRequest mealLogRequest) {
        // Create new object
        MealLog mealLog = new MealLog(mealLogRequest);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateOfMeal;

        // Validation date 
        try {
            dateOfMeal = LocalDate.parse(mealLogRequest.getDateOfMeal(), formatter);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), "Invalid date format. Please provide the date in the format yyyy-MM-dd.", "");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        // Get Member from acccount id context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberService.findByAccountID(principal.getId()).get();

        DailyRecord dailyRecord = dailyRecordService.findByMemberIDAndDate(member.getMemberID(), dateOfMeal).get();

        // new chưa tồn tại record thì create new
        if (dailyRecord == null) {
            dailyRecord = dailyRecordService.save(new DailyRecord(dateOfMeal, member.getDefaultCalories(), member));

        }

        //set record ID
        mealLog.setRecordID(dailyRecord.getRecordID());

        // Store to database
        MealLog mealLogSave = mealLogService.save(mealLog);

        // check result
        if (mealLogSave == null) {
            return new ResponseEntity<>("Failed to create new mealLog", HttpStatus.INTERNAL_SERVER_ERROR);

        }

        // update calories in of daily record
        int caloriesIn = dailyRecord.getTotalCaloriesIn() + mealLogSave.getCalories();

        dailyRecord.setTotalCaloriesIn(caloriesIn);

        dailyRecordService.save(dailyRecord);

        return new ResponseEntity<>(mealLogSave, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete meal log (MEMBER)",
            description = "Delete mealLog by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> deleteMealLogById(@RequestParam int mealLogID) {
        // Find meal log by meal logid
        Optional<MealLog> mealLog = mealLogService.findById(mealLogID);
        // check existed
        if (mealLog.isPresent()) {
            //find Daily record
            Optional<DailyRecord> dailyRecord = dailyRecordService.findById(mealLog.get().getRecordID());
            //Update coloriesIn
            int coloriesIn = dailyRecord.get().getTotalCaloriesIn() - mealLog.get().getCalories();
            dailyRecord.get().setTotalCaloriesIn(coloriesIn);

            // delete meal log
            mealLogService.deleteById(mealLogID);

            //update dailyrecord
            dailyRecordService.save(dailyRecord.get());
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get calories of meals by date (MEMBER)", description = "Retrieve calories of meals")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MealWithCaloriesResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no Tags", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getCaloriesOfMeal")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getCaloriesOfMeal(
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

        // Get member from acccount id context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Member member = memberService.findByAccountID(principal.getId()).get();

        // caculate calories of meal type
        int defaultBreakfast = member.getDefaultCalories() * 30 / 100;
        int defaultLunch = member.getDefaultCalories() * 40 / 100;
        int defaultDinner = member.getDefaultCalories() * 20 / 100;
        int defaultSnack = member.getDefaultCalories() * 10 / 100;
 
        // create default MealWithCaloriesResponse
        List<MealWithCaloriesResponse> caloriesResponses = new ArrayList<>();
        caloriesResponses.add(new MealWithCaloriesResponse(
                EMealType.Breakfast.toString(),
                0,
                defaultBreakfast));
        caloriesResponses.add(new MealWithCaloriesResponse(
                EMealType.Lunch.toString(),
                0,
                defaultLunch));
        caloriesResponses.add(new MealWithCaloriesResponse(
                EMealType.Dinner.toString(),
                0,
                defaultDinner));
        caloriesResponses.add(new MealWithCaloriesResponse(
                EMealType.Snack.toString(),
                0,
                defaultSnack));

        //find dailyRecord
        DailyRecord dailyRecord = dailyRecordService.findByMemberIDAndDate(member.getMemberID(), dateOfMeal).get();

        // new chưa tồn tại record thì create new
        if (dailyRecord == null) {
            dailyRecordService.save(new DailyRecord(dateOfMeal, member.getDefaultCalories(), member));
        } else {
            // Nếu tồn tại recore check tiếp meal log
            // Get all Meal log with accountid and date
            Iterable<MealLog> mealLogs = mealLogService.findByRecordID(dailyRecord.getRecordID());

            // check meal empty
            if (mealLogs.iterator().hasNext()) {
                int breakFast = 0;
                int lunch = 0;
                int dinner = 0;
                int snack = 0;
                for (MealLog mealLog : mealLogs) {
                    switch (mealLog.getMealType()) {
                        case Breakfast ->
                            breakFast += mealLog.getCalories();
                        case Lunch ->
                            lunch += mealLog.getCalories();
                        case Dinner ->
                            dinner += mealLog.getCalories();
                        default ->
                            snack += mealLog.getCalories();
                    }
                }
                caloriesResponses.get(0).setCurrentCalories(breakFast);
                caloriesResponses.get(1).setCurrentCalories(lunch);
                caloriesResponses.get(2).setCurrentCalories(dinner);
                caloriesResponses.get(3).setCurrentCalories(snack);
            }
        }
        return new ResponseEntity<>(caloriesResponses, HttpStatus.OK);
    }
}
