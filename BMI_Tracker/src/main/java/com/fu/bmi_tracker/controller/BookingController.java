/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.Booking;
import com.fu.bmi_tracker.payload.request.CreateBookingTransactionRequest;
import com.fu.bmi_tracker.payload.response.MemberResponse;
import com.fu.bmi_tracker.payload.response.BookingResponse;
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
import com.fu.bmi_tracker.services.BookingService;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Booking", description = "Booking management APIs")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Operation(
            summary = "Create new booking include transaction (MEMBER)",
            description = "Create new booking include transaction")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = BookingResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createTransaction")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> createNewBookinhAndTrasaction(@Valid @RequestBody CreateBookingTransactionRequest createRequest) {
        // Get Member id from acccount id context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // gọi booking service tạo transaction và booking
        Booking booking = bookingService.createBookingTransaction(createRequest, principal.getId());
        // taọ booking response
        BookingResponse bookingResponse = new BookingResponse(booking);

        return new ResponseEntity<>(bookingResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all booking",
            description = "Get all booking")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = BookingResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAllOder() {
        Iterable<Booking> bookings = bookingService.findAll();

        if (!bookings.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // tạo booking response
        List<BookingResponse> bookingResponses = new ArrayList<>();

        bookings.forEach((Booking booking) -> {
            BookingResponse bookingResponse = new BookingResponse(booking);
            bookingResponses.add(bookingResponse);
        });
        return ResponseEntity.ok(bookingResponses);
    }

    @Operation(
            summary = "Get booking by id",
            description = "Get booking by id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = BookingResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getOderByID")
    public ResponseEntity<?> getOderByID(@RequestParam Integer bookingID) {
        Optional<Booking> booking = bookingService.findById(bookingID);

        if (!booking.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo booking response
        BookingResponse bookingResponse = new BookingResponse(booking.get());

        return ResponseEntity.ok(bookingResponse);
    }

    @Operation(
            summary = "Get all booking by member (MEMBER)",
            description = "Get all booking by current member")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = BookingResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByMember")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getAllOderByMember() {
        // Get Member id from acccount id context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Tìm booking bằng account ID
        Iterable<Booking> bookings = bookingService.getBookingByMemberAccountID(principal.getId());

        if (!bookings.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo booking response
        List<BookingResponse> bookingResponses = new ArrayList<>();

        bookings.forEach((Booking booking) -> {
            BookingResponse bookingResponse = new BookingResponse(booking);
            bookingResponses.add(bookingResponse);
        });
        return ResponseEntity.ok(bookingResponses);
    }

    @Operation(
            summary = "Get all booking by member ID",
            description = "Get all booking member by memberID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = BookingResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByMemberID")
    public ResponseEntity<?> getAllOderByMemberID(@RequestParam Integer memberID) {

        // Tìm booking bằng account ID
        Iterable<Booking> bookings = bookingService.getBookingByMemberID(memberID);

        if (!bookings.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo booking response
        List<BookingResponse> bookingResponses = new ArrayList<>();

        bookings.forEach((Booking booking) -> {
            BookingResponse bookingResponse = new BookingResponse(booking);
            bookingResponses.add(bookingResponse);
        });
        return ResponseEntity.ok(bookingResponses);
    }

    @Operation(
            summary = "Get all booking of advisor with Month",
            description = "Get all booking by advisor ID and month of booking date")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = BookingResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/advisor/getByMonth")
    public ResponseEntity<?> getAllOderByAdvisorIDAndMonth(
            @RequestParam("advisorID") Integer advisorID,
            @RequestParam("month") String month) {

        // Tìm booking bằng advisor ID và tháng
        Iterable<Booking> bookings = bookingService.getBookingByAdvisorIDAndMonth(
                advisorID,
                month);

        if (!bookings.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo booking response
        List<BookingResponse> bookingResponses = new ArrayList<>();

        bookings.forEach((Booking booking) -> {
            BookingResponse bookingResponse = new BookingResponse(booking);
            bookingResponses.add(bookingResponse);
        });
        return ResponseEntity.ok(bookingResponses);
    }

    @Operation(
            summary = "Get all booking by advisor (ADVISOR)",
            description = "Get all booking by advisor with  booking date Desc ")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = BookingResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/advisor/getAll")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> getAllOfAdvisor() {

        // lấy acccount id từ context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // gọi booking service lấy danh sách booking của advisor
        List<Booking> bookings = bookingService.getBookingByMemberAdvisor(principal.getId());

        if (!bookings.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo booking response
        List<BookingResponse> bookingResponses = new ArrayList<>();

        bookings.forEach((Booking booking) -> {
            BookingResponse bookingResponse = new BookingResponse(booking);
            bookingResponses.add(bookingResponse);
        });
        return ResponseEntity.ok(bookingResponses);
    }

    // Lấy danh sách member đang đăng ký của advisor
    @Operation(summary = "Receive a list of members currently bookinging from the advisor (ADVISOR)",
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
    public ResponseEntity<?> getCurrentMembersBookingAdvisor() {
        // lấy accountID từ context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // gọi serverice lấy danh sách member
        List<Member> members = bookingService.getCurrentMemeberOfAdvisor(principal.getId());

        // Chuyển đổi member sang member response
        List<MemberResponse> memberResponses = members.stream().map(
                (member)
                -> member.toMemberResponse()).collect(Collectors.toList());
        return new ResponseEntity<>(memberResponses, HttpStatus.OK);
    }

}
