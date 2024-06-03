/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.Menu;
import com.fu.bmi_tracker.payload.request.CreateMenuRequest;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.services.AdvisorService;
import com.fu.bmi_tracker.services.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    AdvisorService advisorService;

    @Operation(
            summary = "Create new menu",
            description = "Create new menu with form")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Menu.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AVISOR')")
    public ResponseEntity<?> createNewMenu(@Valid @RequestBody CreateMenuRequest menuRequest) {
        // lấy account iD từ context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        // Tìm advisor
        Advisor advisor = advisorService.findByAccountID(principal.getId());

        // Kiểm tra advisor
        if (advisor == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Cannot find advisor!"));
        }

        // Create new object
        Menu menu = new Menu(menuRequest, advisor.getAdvisorID());

        // Store to database
        Menu menuSave = menuService.save(menu);

        // check result
        if (menuSave == null) {
            return new ResponseEntity<>("Failed to create new menu", HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<>(menuSave, HttpStatus.CREATED);
    }
}
