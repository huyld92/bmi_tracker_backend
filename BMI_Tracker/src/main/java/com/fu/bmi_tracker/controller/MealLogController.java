/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.CustomerMenu;
import com.fu.bmi_tracker.model.entities.MealLog;
import com.fu.bmi_tracker.payload.request.CreateCustomerMenuRequest;
import com.fu.bmi_tracker.payload.request.CreateMealLogRequest;
import com.fu.bmi_tracker.services.MealLogService;
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

/**
 *
 * @author Duc Huy
 */
@Tag(name = "MealLog", description = "Meal Log management APIs")
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/test/meallog")
public class MealLogController {

    @Autowired
    MealLogService service;

    @Operation(
            summary = "Create new meal log with form",
            description = "Create new meal log",
            tags = {"CustomerMenu"})
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = MealLog.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    public ResponseEntity<?> createNewCustomerMenu(@Valid @RequestBody CreateMealLogRequest createMealLogRequest) {
        // Kiểm tra customer ID
        // kiểm tra foodID
        // Tạo mới MealLog
        // Trả về kết quả
        MealLog mealLog = new MealLog(createMealLogRequest);

        MealLog mealLogSaved = service.save(mealLog);

        return new ResponseEntity<>(mealLogSaved, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Meal Log",
            description = "Retrieve all meal log of customer from customerID")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = MealLog.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getMealLog/{customerID}")
    public ResponseEntity<?> getMealLogByCustomerID(@PathVariable("customerID") Integer customerID) {
        // Tìm meal log bởi customer id => sử dụng userID hay customerID
        List<MealLog> mealLogs;
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

}
