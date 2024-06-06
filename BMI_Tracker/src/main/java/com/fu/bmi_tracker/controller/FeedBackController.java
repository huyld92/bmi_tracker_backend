/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Feedback;
import com.fu.bmi_tracker.payload.request.CreateFeedbackRequest;
import com.fu.bmi_tracker.payload.response.FeedbackForAdminRespone;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fu.bmi_tracker.services.FeedbackService;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author BaoLG
 */
@Tag(name = "FeedBack", description = "FeedBack management APIs")
@RestController
@RequestMapping("/api/feedbacks")
public class FeedBackController {
    
    @Autowired
    FeedbackService service;
    
    @Operation(
            summary = "Create new Plan with form",
            description = "Create new plan with form")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Feedback.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> createNewFeedBack(@RequestBody CreateFeedbackRequest feedbackRequest) {
        
        //Convert createRequest to Plan before save
        Feedback newFeedBack = new Feedback();
        newFeedBack.setTitle(feedbackRequest.getTitle());
        newFeedBack.setDescription(feedbackRequest.getDescription());
        newFeedBack.setStatus(Boolean.FALSE);
        newFeedBack.setType(feedbackRequest.getType());
        newFeedBack.setMemberID(feedbackRequest.getMemberID());
        
        Feedback feedbackSave = service.save(newFeedBack);
        
        if (feedbackSave == null) {
            return new ResponseEntity<>("Failed to create new feedback", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(feedbackSave, HttpStatus.CREATED);
    }
    
    @Operation(
            summary = "Get All Feedback",
            description = "Get All Feedback")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = FeedbackForAdminRespone.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAll")
    //@PreAuthorize("hasRole('ADMIN')") //MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getAllFeedback() {
        
        Iterable<Feedback> feedbackList = service.findAll();
        
        if (!feedbackList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
       
        return new ResponseEntity<>(feedbackList, HttpStatus.OK);
    }
    
    @Operation(
            summary = "Get All Member's Feedback",
            description = "Get feed back by MemberID")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Feedback.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAllByMemberID/{id}")
    //@PreAuthorize("hasRole('ADVISOR')") MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getAllFeedbackByMemberID(@PathVariable("id") int id) {
        
        Iterable<Feedback> feedbackList = service.findFeedbackByMemberID(id);
        
        if (!feedbackList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
       
        return new ResponseEntity<>(feedbackList, HttpStatus.OK);
    }
    
    @Operation(
            summary = "Get All Member's Feedback",
            description = "Get feed back by MemberID")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Feedback.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByID/{id}")
    //@PreAuthorize("hasRole('ADVISOR')") MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getFeedbackByID(@PathVariable("id") int id) {
        
        Optional<Feedback> feedback = service.findById(id);
        
        if (feedback.isPresent()) {
            return new ResponseEntity<>(feedback, HttpStatus.OK);
            
        }  
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /*+
    @Operation(summary = "Deactive a Plan by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/deactive/{id}")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> approveFeedback(@PathVariable("id") int id) {
        Optional<Plan> plan =  planService.findById(id);

        if (plan.isPresent()) {
            plan.get().setActive(Boolean.FALSE);
            return new ResponseEntity<>(planService.save(plan.get()), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Cannot find plan with id{" + id + "}", HttpStatus.NOT_FOUND);
        }
    }*/
}
