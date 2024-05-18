/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.Order;
import com.fu.bmi_tracker.model.entities.Transaction;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.services.TransactionService;
import com.fu.bmi_tracker.services.UserService;
import com.fu.bmi_tracker.services.VNPayService;
import com.fu.bmi_tracker.util.DateTimeUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Duc Huy
 * 
 * */
@Tag(name = "Trasaction", description = "Trasaction management APIs")
@CrossOrigin(origins = "*", maxAge = 3600) 
@RestController
@RequestMapping("/api/test/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    VNPayService vNPayService;

    @Autowired
    UserService userService;

    @Autowired
    DateTimeUtils dateTimeUtils;

    @Operation(
            summary = "Top Up Balance",
            tags = {"Order"},
            description = "Customer only can use")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "401", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping("/payment")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> makePayment(
            @RequestParam("amount") int toUpTotal,
            @RequestParam("orderInfo") String orderInfo,
            HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        String vnpayUrl = vNPayService.makePayment(toUpTotal, orderInfo, baseUrl);

        return ResponseEntity.ok()
                .body(new MessageResponse(vnpayUrl));
    }

    @GetMapping("/vnpay-payment")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> vnpayResult(HttpServletRequest request) {
        int paymentStatus = vNPayService.orderReturn(request);

        if (paymentStatus == 1) {
            System.out.println("URI: " + request.getQueryString());

//            Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            CustomAccountDetailsImpl accountDetailsImpl = (CustomAccountDetailsImpl) principle;
//            if (!"anonymousUser".equals(principle.toString())) {
//                account = ((CustomUserDetails) principle).getAccount();
//            }
            int userID = 1;

            Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            CustomAccountDetailsImpl accountDetailsImpl = (CustomAccountDetailsImpl) principle;

            userID = userService.findByAccountID(accountDetailsImpl.getId()).get().getUserID();

            Integer amount = Integer.valueOf(request.getParameter("vnp_Amount"));

            String paymentTime = request.getParameter("vnp_PayDate");

            LocalDateTime topUpDate = dateTimeUtils.convertStrToLocalDateTime(paymentTime);

            String bankCode = request.getParameter("vnp_BankCode");
            String bankTranNo = request.getParameter("vnp_BankTranNo");
            String cardType = request.getParameter("vnp_CardType");
            String orderInfo = request.getParameter("vnp_OrderInfo");

            Transaction transaction = new Transaction(bankCode, bankTranNo,
                    cardType, amount, orderInfo,
                    topUpDate, userID);

//            transactionService.save(transaction);

            return new ResponseEntity<>(transaction, HttpStatus.OK);
        }
        return new ResponseEntity<>("orderfail", HttpStatus.FAILED_DEPENDENCY);

    }
}
