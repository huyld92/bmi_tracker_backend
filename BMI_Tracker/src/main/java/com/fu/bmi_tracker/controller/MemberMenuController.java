/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.MemberMenu;
import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.payload.request.CreateMemberMenuRequest;
import com.fu.bmi_tracker.services.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fu.bmi_tracker.services.MemberMenuService;
import java.util.stream.StreamSupport;
import org.springframework.security.access.prepost.PreAuthorize;
import com.fu.bmi_tracker.services.MemberService;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "MemberMenu", description = "MemberMenu management APIs")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/member/menu")
public class MemberMenuController {

    @Autowired
    MemberMenuService service;

    @Autowired
    FoodService foodService;

    @Autowired
    MemberService memberService;

    @Operation(
            summary = "Create new member menu with form",
            description = "Create new member menu with form",
            tags = {"TRAINER"})
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = MemberMenu.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> createNewMemberMenu(@RequestBody CreateMemberMenuRequest createMemberMenuRequest) {
        //Get list food by food id list
        Iterable<Food> foods = foodService.findByFoodIDIn(createMemberMenuRequest.getFoodIDs());
        long size = StreamSupport.stream(foods.spliterator(), false).count();
        System.out.println(size);
        if (size < createMemberMenuRequest.getFoodIDs().size()) {
            return new ResponseEntity<>("Not found food id!", HttpStatus.NOT_FOUND);
        }

        // Get member by member id
        Optional<Member> member = memberService.findById(createMemberMenuRequest.getMemberID());
        if (!member.isPresent()) {
            return new ResponseEntity<>("Cannot find member menu with member id{" + createMemberMenuRequest.getMemberID() + "}", HttpStatus.NOT_FOUND
            );
        }
        //Create new list member menu to store
        List<MemberMenu> memberMenus = new ArrayList<>();

        // add food to list member menu
        foods.forEach(food -> {
            memberMenus.add(new MemberMenu(member.get(), food));
        });

        // List member menu response
        List<MemberMenu> memberMenusResponse = service.saveAll(memberMenus);

        return new ResponseEntity<>(memberMenusResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get menu of member",
            description = "Give member id to retrieve list menu",
            tags = {"MemberMenu"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Menus retrieved successfully",
                content = {
                    @Content(schema = @Schema(implementation = MemberMenu.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/get/{memberID}")
    public ResponseEntity<?> getAllMenuByMemberID(@PathVariable("memberID") Integer memberID) {
        // Logic to retrieve menus for the given member ID
        List<MemberMenu> memberMenus = service.findAllByMemberID(memberID);

        if (memberMenus.isEmpty()) {
            return new ResponseEntity<>("Cannot find member menu with member id{" + memberID + "}", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(memberMenus, HttpStatus.OK);
    }

    @Operation(summary = "Delete a menu of member by list food Id", tags = {"MemberMenu"})
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFoodMenu(@RequestParam int memberID, @RequestParam int foodID) {
        service.deleteByMemberMemberIDAndFoodFoodID(memberID, foodID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete a menu of member by list food Id", tags = {"MemberMenu"})
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllFoodMenu(@RequestParam int memberID) {
        service.deleteAllByMemberMemberID(memberID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
