/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Notification;
import com.fu.bmi_tracker.payload.response.NotificationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
@Tag(name = "Notification", description = "Notification management APIs")
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Operation(
            summary = "Get all notification for user",
            description = "Logged with account id to get all notification ")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = NotificationResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/get-all-for-user")
    @PreAuthorize("hasRole('ADMIN')or hasRole('MEMBER')or hasRole('MANAGER')or hasRole('ADVISOR')")
    public ResponseEntity<?> getAllForUser() {
        // Lấy danh sách menu
//        Iterable<Notification> notifications = new Iteratỏ
//
//        // kiểm tra menu trống
//        if (!notifications.iterator().hasNext()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
        List<Notification> notifications = new ArrayList<>();
        notifications.add(new Notification(1, "Test notification", "title test", LocalDateTime.now(),
                Boolean.TRUE, Boolean.TRUE));
        notifications.add(new Notification(2, "Test notification2", "title test 2", LocalDateTime.now(),
                Boolean.TRUE, Boolean.TRUE));
        notifications.add(new Notification(3, "Test notification3", "title test 3", LocalDateTime.now(),
                Boolean.TRUE, Boolean.TRUE));
        // tạo menu response
        List<NotificationResponse> notificationResponses = new ArrayList<>();

        notifications.forEach(notification -> {
            notificationResponses.add(new NotificationResponse(notification));
        });

        return new ResponseEntity<>(notificationResponses, HttpStatus.OK);
    }

}
