/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.User;
import com.fu.bmi_tracker.model.entities.UserMenu;
import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.payload.request.CreateUserMenuRequest;
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
import com.fu.bmi_tracker.services.UserMenuService;
import com.fu.bmi_tracker.services.UserService;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "UserMenu", description = "UserMenu management APIs")
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/test/user/menu")
public class UserMenuController {

    @Autowired
    UserMenuService service;

    @Autowired
    FoodService foodService;

    @Autowired
    UserService userService;

    @Operation(
            summary = "Create new user menu with form",
            description = "Create new user menu with form",
            tags = {"UserMenu"})
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = UserMenu.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    public ResponseEntity<?> createNewUserMenu(@RequestBody CreateUserMenuRequest createUserMenuRequest) {
        //Get list food by food id list
        List<Food> foods = foodService.findByFoodIDIn(createUserMenuRequest.getFoodIDs());
        if (foods.size() < createUserMenuRequest.getFoodIDs().size()) {
            return new ResponseEntity<>("Not found food id!", HttpStatus.NOT_FOUND);
        }

        // Get user by user id
        Optional<User> user = userService.findById(createUserMenuRequest.getUserID());
        if (!user.isPresent()) {
            return new ResponseEntity<>("Cannot find user menu with user id{" + createUserMenuRequest.getUserID() + "}", HttpStatus.NOT_FOUND
            );
        }
        //Create new list user menu to store
        List<UserMenu> userMenus = new ArrayList<>();

        // add food to list user menu
        foods.forEach(food -> {
            userMenus.add(new UserMenu(user.get(), food));
        });

        // List user menu response
        List<UserMenu> userMenusResponse = service.saveAll(userMenus);

        return new ResponseEntity<>(userMenusResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get menu of user",
            description = "Give user id to retrieve list menu",
            tags = {"UserMenu"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Menus retrieved successfully",
                content = {
                    @Content(schema = @Schema(implementation = UserMenu.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/get/{userID}")
    public ResponseEntity<?> getAllMenuByUserID(@PathVariable("userID") Integer userID) {
        // Logic to retrieve menus for the given user ID
        List<UserMenu> userMenus = service.findAllByUserID(userID);

        if (userMenus.isEmpty()) {
            return new ResponseEntity<>("Cannot find user menu with user id{" + userID + "}", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userMenus, HttpStatus.OK);
    }

    @Operation(summary = "Delete a menu of user by list food Id", tags = {"UserMenu"})
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFoodMenu(@RequestParam int userID, @RequestParam int foodID) {
        service.deleteByUserUserIDAndFoodFoodID(userID, foodID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete a menu of user by list food Id", tags = {"UserMenu"})
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllFoodMenu(@RequestParam int userID) {
        service.deleteAllByUserUserID(userID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
