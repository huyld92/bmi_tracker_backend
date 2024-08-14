/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.AdvisorSubscription;
import com.fu.bmi_tracker.model.entities.Notification;
import com.fu.bmi_tracker.payload.request.CreateSubscriptionTransactionRequest;
import com.fu.bmi_tracker.payload.response.AdvisorDetailsResponse;
import com.fu.bmi_tracker.payload.response.MemberResponse;
import com.fu.bmi_tracker.payload.response.SubscriptionResponse;
import com.fu.bmi_tracker.repository.AccountRepository;
import com.fu.bmi_tracker.repository.NotificationRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fu.bmi_tracker.services.SubscriptionService;
import com.fu.bmi_tracker.services.impl.NotificationServiceImpl;
import java.time.LocalDateTime;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Subscription", description = "Subscription management APIs")
@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    SubscriptionService subscriptionService;
    
    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    NotificationServiceImpl notificationServiceImpl;

    @Operation(
            summary = "Create new subscription include transaction (MEMBER)",
            description = "Create new subscription include transaction")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = SubscriptionResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createTransaction")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> createNewSubscriptionAndTrasaction(@Valid @RequestBody CreateSubscriptionTransactionRequest createRequest) {
        // Get Member id from acccount id context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // gọi subscription service tạo transaction và subscription
        AdvisorSubscription subscription = subscriptionService.createSubscriptionTransaction(createRequest, principal.getId());      
        
        // taọ subscription response
        SubscriptionResponse subscriptionResponse = new SubscriptionResponse(subscription);

        return new ResponseEntity<>(subscriptionResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all subscription",
            description = "Get all subscription")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = SubscriptionResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAllOder() {
        Iterable<AdvisorSubscription> subscriptions = subscriptionService.findAll();

        if (!subscriptions.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // tạo subscription response
        List<SubscriptionResponse> subscriptionResponses = new ArrayList<>();

        subscriptions.forEach((AdvisorSubscription subscription) -> {
            SubscriptionResponse subscriptionResponse = new SubscriptionResponse(subscription);
            subscriptionResponses.add(subscriptionResponse);
        });
        return ResponseEntity.ok(subscriptionResponses);
    }

    @Operation(
            summary = "Get subscription by id",
            description = "Get subscription by id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = SubscriptionResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getOderByID")
    public ResponseEntity<?> getOderByID(@RequestParam Integer subscriptionID) {
        Optional<AdvisorSubscription> subscription = subscriptionService.findById(subscriptionID);

        if (!subscription.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo subscription response
        SubscriptionResponse subscriptionResponse = new SubscriptionResponse(subscription.get());

        return ResponseEntity.ok(subscriptionResponse);
    }

    @Operation(
            summary = "Get all subscription by member (MEMBER)",
            description = "Get all subscription by current member")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = SubscriptionResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByMember")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getAllSubscriptionByMember() {
        // Get Member id from acccount id context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Tìm subscription bằng account ID
        Iterable<AdvisorSubscription> subscriptions = subscriptionService.getSubscriptionByMemberAccountID(principal.getId());

        if (!subscriptions.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo subscription response
        List<SubscriptionResponse> subscriptionResponses = new ArrayList<>();

        subscriptions.forEach((AdvisorSubscription subscription) -> {
            SubscriptionResponse subscriptionResponse = new SubscriptionResponse(subscription);
            subscriptionResponses.add(subscriptionResponse);
        });
        return ResponseEntity.ok(subscriptionResponses);
    }

    @Operation(
            summary = "Get all subscription by member ID",
            description = "Get all subscription member by memberID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = SubscriptionResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByMemberID")
    public ResponseEntity<?> getAllSubscriptionByMemberID(@RequestParam Integer memberID) {

        // Tìm subscription bằng account ID
        Iterable<AdvisorSubscription> subscriptions = subscriptionService.getSubscriptionByMemberID(memberID);

        if (!subscriptions.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo subscription response
        List<SubscriptionResponse> subscriptionResponses = new ArrayList<>();

        subscriptions.forEach((AdvisorSubscription subscription) -> {
            SubscriptionResponse subscriptionResponse = new SubscriptionResponse(subscription);
            subscriptionResponses.add(subscriptionResponse);
        });
        return ResponseEntity.ok(subscriptionResponses);
    }

    @Operation(
            summary = "Get all subscription of advisor with Month",
            description = "Get all subscription by advisor ID and month of subscription date")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = SubscriptionResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/advisor/getByMonth")
    public ResponseEntity<?> getAllSubscriptionByAdvisorIDAndMonth(
            @RequestParam("advisorID") Integer advisorID,
            @RequestParam("month") String month) {

        // Tìm subscription bằng advisor ID và tháng
        Iterable<AdvisorSubscription> subscriptions = subscriptionService.getSubscriptionByAdvisorIDAndMonth(
                advisorID,
                month);

        if (!subscriptions.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo subscription response
        List<SubscriptionResponse> subscriptionResponses = new ArrayList<>();

        subscriptions.forEach((AdvisorSubscription subscription) -> {
            SubscriptionResponse subscriptionResponse = new SubscriptionResponse(subscription);
            subscriptionResponses.add(subscriptionResponse);
        });
        return ResponseEntity.ok(subscriptionResponses);
    }

    @Operation(
            summary = "Get all subscription by advisor (ADVISOR)",
            description = "Get all subscription by advisor with  subscription date Desc ")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = SubscriptionResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/advisor/getAll")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> getAllOfAdvisor() {

        // lấy acccount id từ context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // gọi subscription service lấy danh sách subscription của advisor
        List<AdvisorSubscription> subscriptions = subscriptionService.getSubscriptionByMemberAdvisor(principal.getId());

        if (!subscriptions.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo subscription response
        List<SubscriptionResponse> subscriptionResponses = new ArrayList<>();

        subscriptions.forEach((AdvisorSubscription subscription) -> {
            SubscriptionResponse subscriptionResponse = new SubscriptionResponse(subscription);
            subscriptionResponses.add(subscriptionResponse);
        });
        return ResponseEntity.ok(subscriptionResponses);
    }

    // Lấy danh sách member đang đăng ký của advisor
    @Operation(summary = "Receive a list of members currently subscriptioning from the advisor (ADVISOR)",
            description = "Login with advisor account and get list member")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MemberResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no meal", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/advisor/getCurrentMember")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> getCurrentMembersSubscriptionAdvisor() {
        // lấy accountID từ context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // gọi serverice lấy danh sách member
        List<Member> members = subscriptionService.getCurrentMemeberOfAdvisor(principal.getId());

        // Chuyển đổi member sang member response
        List<MemberResponse> memberResponses = members.stream().map(
                (member)
                -> member.toMemberResponse()).collect(Collectors.toList());
        return new ResponseEntity<>(memberResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get advisor of member (MEMBER)",
            description = "Get current advisor support member with subscription status Pending")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = AdvisorDetailsResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/advisor/getByMember")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getAdvisorFromSubscription() {
        // lây thông tin của advisor cho member với trạng thái subscription pending
        
        // lấy thông tin đăng nhập từ context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // GỌi subscription service tìm advisor 
        AdvisorDetailsResponse advisorDetailsResponse = subscriptionService.getAdvisorOfMember(principal.getId());

        if (advisorDetailsResponse == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }

        return new ResponseEntity<>(advisorDetailsResponse, HttpStatus.OK);

    }
}
