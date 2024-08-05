/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.Feedback;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.payload.request.CreateFeedbackRequest;
import com.fu.bmi_tracker.payload.response.FeedbackForAdminRespone;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.services.FeedbackService;
import com.fu.bmi_tracker.services.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author BaoLG
 */
@Tag(name = "Feedback", description = "Feedback management APIs")
@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    MemberService memberService;

    @Operation(
            summary = "Create new Feedback with form",
            description = "Create new feedback with form")
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
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        Member member = memberService.findByAccountID(principal.getId())
                .orElseThrow(() -> new EntityNotFoundException("No member found!"));

        //Convert createRequest to Feedback before save
        Feedback newFeedBack = new Feedback();
        newFeedBack.setTitle(feedbackRequest.getTitle());
        newFeedBack.setDescription(feedbackRequest.getDescription());
        newFeedBack.setStatus(Boolean.FALSE);
        newFeedBack.setType(feedbackRequest.getType());
        newFeedBack.setMember(member);

        Feedback feedbackSave = feedbackService.save(newFeedBack);

        if (feedbackSave == null) {
            return new ResponseEntity<>(new MessageResponse("Failed to create new feedback"), HttpStatus.INTERNAL_SERVER_ERROR);
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
        // gọi service lấy tất cả feedback
        Iterable<Feedback> feedbackList = feedbackService.findAll();

        // kiểm tra kết quả
        if (!feedbackList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo Feedback response
        List<FeedbackForAdminRespone> feedbackResponseAlls = new ArrayList<>();
        feedbackList.forEach(feedback -> feedbackResponseAlls.add(new FeedbackForAdminRespone(feedback)));

        return new ResponseEntity<>(feedbackResponseAlls, HttpStatus.OK);
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

        Iterable<Feedback> feedbackList = feedbackService.findFeedbackByMemberID(id);

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

        Optional<Feedback> feedback = feedbackService.findById(id);

        if (feedback.isPresent()) {
            return new ResponseEntity<>(feedback, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Do feedback need reject
    @Operation(summary = "Approve a feedback")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping("/approve/{id}")
    //@PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> approveFeedback(@PathVariable("id") int id) {
        Optional<Feedback> feedback = feedbackService.findById(id);

        if (feedback.isPresent()) {

            feedback.get().setStatus(Boolean.TRUE);

            feedbackService.save(feedback.get());

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(new MessageResponse("Cannot find feedback with id{" + id + "}"), HttpStatus.NOT_FOUND);
        }
    }
}
