/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.Order;
import com.fu.bmi_tracker.payload.request.CreateOrderTransactionRequest;
import com.fu.bmi_tracker.payload.response.OrderResponse;
import com.fu.bmi_tracker.services.OrderService;
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

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Order", description = "Order management APIs")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Operation(
            summary = "Create new order include transaction (MEMBER)",
            description = "Create new order include transaction")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = OrderResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createTransaction")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> createNewOrderAndTrasaction(@Valid @RequestBody CreateOrderTransactionRequest createRequest) {
        // Get Member id from acccount id context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // gọi order service tạo transaction và order
        Order order = orderService.createOrderTransaction(createRequest, principal.getId());
        // taọ order response
        OrderResponse orderResponse = new OrderResponse(order);

        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all order",
            description = "Get all order")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = OrderResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAllOder() {
        Iterable<Order> orders = orderService.findAll();

        if (!orders.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // tạo order response
        List<OrderResponse> orderResponses = new ArrayList<>();

        orders.forEach((Order order) -> {
            OrderResponse orderResponse = new OrderResponse(order);
            orderResponses.add(orderResponse);
        });
        return ResponseEntity.ok(orderResponses);
    }

    @Operation(
            summary = "Get order by id",
            description = "Get order by id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = OrderResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getOderByID")
    public ResponseEntity<?> getOderByID(@RequestParam Integer orderID) {
        Optional<Order> order = orderService.findById(orderID);

        if (!order.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo order response
        OrderResponse orderResponse = new OrderResponse(order.get());

        return ResponseEntity.ok(orderResponse);
    }

    @Operation(
            summary = "Get all order by member (MEMBER)",
            description = "Get all order by current member")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = OrderResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByMember")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getAllOderByMember() {
        // Get Member id from acccount id context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Tìm order bằng account ID
        Iterable<Order> orders = orderService.getOrderByMemberAccountID(principal.getId());

        if (!orders.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo order response
        List<OrderResponse> orderResponses = new ArrayList<>();

        orders.forEach((Order order) -> {
            OrderResponse orderResponse = new OrderResponse(order);
            orderResponses.add(orderResponse);
        });
        return ResponseEntity.ok(orderResponses);
    }

    @Operation(
            summary = "Get all order by member ID",
            description = "Get all order member by memberID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = OrderResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByMemberID")
    public ResponseEntity<?> getAllOderByMemberID(@RequestParam Integer memberID) {

        // Tìm order bằng account ID
        Iterable<Order> orders = orderService.getOrderByMemberID(memberID);

        if (!orders.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo order response
        List<OrderResponse> orderResponses = new ArrayList<>();

        orders.forEach((Order order) -> {
            OrderResponse orderResponse = new OrderResponse(order);
            orderResponses.add(orderResponse);
        });
        return ResponseEntity.ok(orderResponses);
    }

    @Operation(
            summary = "Get all order by advisor ID",
            description = "Get all order by advisor ID and month of order date")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = OrderResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByAdvisorID")
    public ResponseEntity<?> getAllOderByAdvisorIDAndMonth(
            @RequestParam("advisorID") Integer advisorID,
            @RequestParam("month") String month) {

        // Tìm order bằng advisor ID và tháng
        Iterable<Order> orders = orderService.getOrderByAdvisorIDAndMonth(
                advisorID,
                month);

        if (!orders.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo order response
        List<OrderResponse> orderResponses = new ArrayList<>();

        orders.forEach((Order order) -> {
            OrderResponse orderResponse = new OrderResponse(order);
            orderResponses.add(orderResponse);
        });
        return ResponseEntity.ok(orderResponses);
    }
}
