/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.WorkoutHistory;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.payload.response.WorkoutHistoryResponse;
import com.fu.bmi_tracker.services.MemberService;
import com.fu.bmi_tracker.services.WorkoutHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "WorkoutHistory", description = "Workout history management APIs")
@RestController
@RequestMapping("/api/workout-history")
public class WorkoutHistoryController {

    @Autowired
    WorkoutHistoryService workoutHistoryService;
    @Autowired
    MemberService memberService;

    @Operation(
            summary = "Get all workout history of member (MEMBER)",
            description = "Login with member role and get all workout history")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = WorkoutHistoryResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "member/getAll")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getAllWorkoutHistoryOfMember() {
        // lấy account iD từ context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // gọi member repository tìm thông tin member
        Member member = memberService.findByAccountID(principal.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find member!"));

        // Lấy danh sách workout of member
        Iterable<WorkoutHistory> workoutHistories = workoutHistoryService.getWorkoutHistoryOfMember(member.getMemberID());

        // kiểm tra workout trống
        if (!workoutHistories.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo Workout History Response
        List<WorkoutHistoryResponse> workoutHistoryResponses = new ArrayList<>();

        workoutHistories.forEach(workoutHistory -> {
            workoutHistoryResponses.add(new WorkoutHistoryResponse(
                    workoutHistory.getWorkoutHistoryID(),
                    workoutHistory.getDateOfAssigned(),
                    workoutHistory.getWorkout().getWorkoutID(),
                    workoutHistory.getWorkout().getWorkoutName(),
                    workoutHistory.getWorkout().getWorkoutDescription(),
                    workoutHistory.getWorkout().getTotalCloriesBurned(),
                    member.getMemberID(),
                    member.getAccount().getFullName(),
                    workoutHistory.getIsActive()));
        });

        return new ResponseEntity<>(workoutHistoryResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all workout history of member by id",
            description = "Provide member id  and get all workout history")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = WorkoutHistoryResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "member/getByID")
    //    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllWorkoutHistoryOfMember(@RequestParam Integer memberID) {
        // gọi member service tìm thông tin member
        Member member = memberService.findById(memberID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find member!"));

        // Lấy danh sách workout of member
        Iterable<WorkoutHistory> workoutHistories = workoutHistoryService.getWorkoutHistoryOfMember(member.getMemberID());

        // kiểm tra workout trống
        if (!workoutHistories.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo Workout History Response
        List<WorkoutHistoryResponse> workoutHistoryResponses = new ArrayList<>();

        workoutHistories.forEach(workoutHistory -> {
            workoutHistoryResponses.add(new WorkoutHistoryResponse(
                    workoutHistory.getWorkoutHistoryID(),
                    workoutHistory.getDateOfAssigned(),
                    workoutHistory.getWorkout().getWorkoutID(),
                    workoutHistory.getWorkout().getWorkoutName(),
                    workoutHistory.getWorkout().getWorkoutDescription(),
                    workoutHistory.getWorkout().getTotalCloriesBurned(),
                    member.getMemberID(),
                    member.getAccount().getFullName(),
                    workoutHistory.getIsActive()));
        });

        return new ResponseEntity<>(workoutHistoryResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Assign workout for member (ADVISOR)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MessageResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping(value = "/assignWorkout")
//    @PreAuthorize("hasRole('AVISOR')")
    public ResponseEntity<?> assignWorkout(@Valid @RequestParam Integer workoutID, @RequestParam Integer memberID) {
        // gọi service assign workout cho member
        workoutHistoryService.assignWorkoutToMember(workoutID, memberID);

        return new ResponseEntity<>(new MessageResponse("Assign workout to member success"), HttpStatus.OK);
    }

}
