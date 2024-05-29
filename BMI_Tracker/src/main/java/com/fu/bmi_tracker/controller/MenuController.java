/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.services.MenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Menu", description = "Menu management APIs")
@RestController
@RequestMapping("/api/menus")
public class MenuController {

    @Autowired
    MenuService menuService;

//        @Operation(
//            summary = "Create new menu",
//            description = "Create new menu with form")
//    @ApiResponses({
//        @ApiResponse(responseCode = "201", content = {
//            @Content(schema = @Schema(implementation = Menu.class), mediaType = "application/json")}),
//        @ApiResponse(responseCode = "403", content = {
//            @Content(schema = @Schema())}),
//        @ApiResponse(responseCode = "500", content = {
//            @Content(schema = @Schema())})})
//    @PostMapping(value = "/createNew")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('AVISOR')")
//    public ResponseEntity<?> createNewMenu(@Valid @RequestBody CreateMenuRequest menuRequest) {
//        // Create new object
//        Menu menu = new Menu(menuRequest);
//
//        // Store to database
//        Menu menuSave = service.save(menu);
//
//        // check result
//        if (menuSave == null) {
//            return new ResponseEntity<>("Failed to create new menu", HttpStatus.INTERNAL_SERVER_ERROR);
//
//        }
//
//        return new ResponseEntity<>(menuSave, HttpStatus.CREATED);
//    }
}
