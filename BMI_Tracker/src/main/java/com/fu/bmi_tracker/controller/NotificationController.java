/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.Notification;
import com.fu.bmi_tracker.payload.response.NotificationResponse;
import com.fu.bmi_tracker.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Notification", description = "Notification management APIs")
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @Operation(
            summary = "Get all notification for user",
            description = "Logged with account id to get all notification ")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = NotificationResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "Response empty list"),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/get-all-for-user")
    @PreAuthorize("hasRole('ADMIN')or hasRole('MEMBER')or hasRole('MANAGER')or hasRole('ADVISOR')")
    public ResponseEntity<?> getAllForUser() {
        // lấy id account hiện tại trong context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        Iterable<Notification> notifications = notificationService.findByAccountID(principal.getId());

        if (!notifications.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo menu response
        List<NotificationResponse> notificationResponses = new ArrayList<>();

        notifications.forEach(notification -> {
            notificationResponses.add(new NotificationResponse(notification));
        });

        return new ResponseEntity<>(notificationResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Mark as read notification by notification id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Success mark as read notification"),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping(value = "/mark-as-read")
    public ResponseEntity<?> markAsRead(@RequestParam Integer notificationID) {
        // tìm notificaion
        Notification notification = notificationService.findById(notificationID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find notification!"));
        //set True is read
        notification.setIsRead(Boolean.TRUE);

        notificationService.save(notification);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Mark as read all notification (LOGIN)")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Success mark as read notification"),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping(value = "/read-all")
    public ResponseEntity<?> markAsReadAll() {
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // gọi service caajap nhật active notificaion 
        notificationService.markAsReadAll(principal.getId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Delete notification")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Delete notification"),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteNotification(@RequestParam Integer notificationID) {
        // delete notificaion
        notificationService.deleteByID(notificationID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public void createNotification() {

    }

}
