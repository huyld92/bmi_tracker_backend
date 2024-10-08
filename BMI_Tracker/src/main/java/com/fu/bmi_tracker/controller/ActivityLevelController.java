/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.ActivityLevel;
import com.fu.bmi_tracker.payload.response.ActivityLevelResponse;
import com.fu.bmi_tracker.services.ActivityLevelService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Activity Level", description = "Activity level management APIs")
@RestController
@RequestMapping("/api/activitylevel")
public class ActivityLevelController {

    @Autowired
    ActivityLevelService activityLevelService;

    @Operation(
            summary = "Get all activity level",
            description = "Only MEMBER and Admin using")
    @ApiResponses({
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = ActivityLevel.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAll")
    @PreAuthorize("hasRole('MEMBER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllActivityLevel() {

        Iterable<ActivityLevel> activityLevels = activityLevelService.findAll();

        if (!activityLevels.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(activityLevels, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all activity level with details",
            description = "Only MEMBER using")
    @ApiResponses({
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = ActivityLevelResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAllWithDetails")
    @PreAuthorize("hasRole('MEMBER') ")
    public ResponseEntity<?> getAllActivityLevelWithDetails() {

        List<ActivityLevelResponse> activityLevels = activityLevelService.findAllActivityLevelsWithDetails();

        if (activityLevels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(activityLevels, HttpStatus.OK);
    }
}
