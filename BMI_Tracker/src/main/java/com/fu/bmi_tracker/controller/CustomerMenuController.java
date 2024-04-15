/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Customer;
import com.fu.bmi_tracker.model.entities.CustomerMenu;
import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.payload.request.CreateCustomerMenuRequest;
import com.fu.bmi_tracker.services.CustomerMenuService;
import com.fu.bmi_tracker.services.CustomerService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "CustomerMenu", description = "CustomerMenu management APIs")
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/test/customer/menu")
public class CustomerMenuController {

    @Autowired
    CustomerMenuService service;

    @Autowired
    FoodService foodService;

    @Autowired
    CustomerService customerService;

    @Operation(
            summary = "Create new customer menu with form",
            description = "Create new customer menu with form: include MultiplePath for customerMenu photo",
            tags = {"CustomerMenu"})
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = CustomerMenu.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    public ResponseEntity<?> createNewCustomerMenu(@RequestBody CreateCustomerMenuRequest createCustomerMenuRequest) {
        //Get list food by food id list
        List<Food> foods = foodService.findByFoodIDIn(createCustomerMenuRequest.getFoodIDs());
        if (foods.size() < createCustomerMenuRequest.getFoodIDs().size()) {
            return new ResponseEntity<>("Not found food id!", HttpStatus.NOT_FOUND);
        }

        // Get customer by customer id
        Optional<Customer> customer = customerService.findById(createCustomerMenuRequest.getCustomerID());
        if (!customer.isPresent()) {
            return new ResponseEntity<>("Cannot find customer menu with customer id{" + createCustomerMenuRequest.getCustomerID() + "}", HttpStatus.NOT_FOUND
            );
        }
        //Create new list customer menu to store
        List<CustomerMenu> customerMenus = new ArrayList<>();

        // add food to list customer menu
        foods.forEach(food -> {
            customerMenus.add(new CustomerMenu(customer.get(), food));
        });

        // List customer menu response
        List<CustomerMenu> customerMenusResponse = service.saveAll(customerMenus);

        return new ResponseEntity<>(customerMenusResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get menu of customer",
            description = "Give customer id to retrieve ",
            tags = {"CustomerMenu"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Menus retrieved successfully",
                content = {
                    @Content(schema = @Schema(implementation = CustomerMenu.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/get/{customerID}")
    public ResponseEntity<?> getAllMenuByCustomerID(@PathVariable("customerID") Integer customerID) {
        // Logic to retrieve menus for the given customer ID
        List<CustomerMenu> customerMenus = service.findAllByCustomerID(customerID);

        if (customerMenus.isEmpty()) {
            return new ResponseEntity<>("Cannot find customer menu with customer id{" + customerID + "}", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(customerMenus, HttpStatus.OK);
    }

    @Operation(summary = "Delete a menu of customer by list food Id", tags = {"CustomerMenu"})
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/delete")
    public ResponseEntity<?> deleteFoodMenu(@RequestParam int customerID, @RequestParam int foodID) {
        service.deleteByCustomerCustomerIdAndFoodFoodID(customerID, foodID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
