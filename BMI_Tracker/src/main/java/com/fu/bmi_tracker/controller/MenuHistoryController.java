/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.MenuHistory;
import com.fu.bmi_tracker.payload.response.MenuHistoryResponse;
import com.fu.bmi_tracker.payload.response.MenuResponseAll;
import com.fu.bmi_tracker.services.MemberService;
import com.fu.bmi_tracker.services.MenuHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "MenuHistory", description = "Menu history management APIs")
@RestController
@RequestMapping("/api/menu-history")
public class MenuHistoryController {

    @Autowired
    MenuHistoryService menuHistoryService;

    @Autowired
    MemberService memberService;

    @Operation(
            summary = "Get all menu history of member (MEMBER)",
            description = "Login with Member role and Get all menu include food")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MenuHistoryResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "member/getAll")
    //    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllMenuHistoryOfMember() {
        // lấy account iD từ context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // gọi member repository tìm thông tin member
        Member member = memberService.findByAccountID(principal.getId())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find member!"));

        // Lấy danh sách menu of member
        Iterable<MenuHistory> menuHistorys = menuHistoryService.getMenuHistoryOfMember(member.getMemberID());

        // kiểm tra menu trống
        if (!menuHistorys.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo Menu History Response
        List<MenuHistoryResponse> menuHistoryResponses = new ArrayList<>();

        menuHistorys.forEach(menuHistory -> {
            menuHistoryResponses.add(new MenuHistoryResponse(
                    menuHistory.getMenuHistoryID(),
                    menuHistory.getDateOfAssigned(),
                    menuHistory.getMenu().getMenuID(),
                    menuHistory.getMenu().getMenuPhoto(),
                    menuHistory.getMenu().getTotalCalories(),
                    menuHistory.getMenu().getMenuDescription(),
                    menuHistory.getMenu().getMenuName(),
                    member.getMemberID(),
                    member.getAccount().getFullName(),
                    menuHistory.getIsActive()));
        });

        return new ResponseEntity<>(menuHistoryResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all menu history of member by member id",
            description = "Get menu history by member id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MenuHistoryResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "get-all-by-member-id")
    //    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllMenuHistoryByMemberID(@RequestParam Integer memberID) {
        // gọi member repository tìm thông tin member
        Member member = memberService.findById(memberID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find member id{" + memberID + "}!"));

        // Lấy danh sách menu of member
        Iterable<MenuHistory> menuHistorys = menuHistoryService.getMenuHistoryOfMember(memberID);

        // kiểm tra menu trống
        if (!menuHistorys.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo Menu History Response
        List<MenuHistoryResponse> menuHistoryResponses = new ArrayList<>();

        menuHistorys.forEach(menuHistory -> {
            menuHistoryResponses.add(new MenuHistoryResponse(
                    menuHistory.getMenuHistoryID(),
                    menuHistory.getDateOfAssigned(),
                    menuHistory.getMenu().getMenuID(),
                    menuHistory.getMenu().getMenuPhoto(),
                    menuHistory.getMenu().getTotalCalories(),
                    menuHistory.getMenu().getMenuDescription(),
                    menuHistory.getMenu().getMenuName(),
                    member.getMemberID(),
                    member.getAccount().getFullName(),
                    menuHistory.getIsActive()));
        });

        return new ResponseEntity<>(menuHistoryResponses, HttpStatus.OK);
    }
}
