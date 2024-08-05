/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.Menu;
import com.fu.bmi_tracker.model.entities.MenuFood;
import com.fu.bmi_tracker.payload.request.CreateMenuFoodRequest;
import com.fu.bmi_tracker.payload.request.CreateMenuRequest;
import com.fu.bmi_tracker.payload.request.MenuFoodRequest;
import com.fu.bmi_tracker.payload.request.UpdateMenuRequest;
import com.fu.bmi_tracker.payload.response.FoodResponse;
import com.fu.bmi_tracker.payload.response.MenuFoodBasicResponse;
import com.fu.bmi_tracker.payload.response.MenuFoodResponse;
import com.fu.bmi_tracker.payload.response.MenuResponse;
import com.fu.bmi_tracker.payload.response.MenuResponseAll;
import com.fu.bmi_tracker.payload.response.MenuResponseBasicFood;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.services.AdvisorService;
import com.fu.bmi_tracker.services.FoodService;
import com.fu.bmi_tracker.services.MenuFoodService;
import com.fu.bmi_tracker.services.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    FoodService foodService;

    @Autowired
    MenuFoodService menuFoodService;

    @Operation(
            summary = "Create new menu (Advisor)",
            description = "Create new menu with form")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = MenuResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> createNewMenu(@Valid @RequestBody CreateMenuRequest menuRequest) {
        // lấy account iD từ context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Menu menu = menuService.createNewMenu(menuRequest, principal.getId());

        if (menu == null) {
            return new ResponseEntity<>(new MessageResponse("Create new menu failed!"), HttpStatus.BAD_REQUEST);
        }
        // tạo menu food responses
        List<MenuFoodResponse> menuFoodResponses = new ArrayList<>();
        menu.getMenuFoods().forEach(menuFood -> {

            menuFoodResponses.add(new MenuFoodResponse(
                    menuFood.getFood(),
                    menuFood.getMealType(),
                    menu.getIsActive()));
        });

        // tạo menu response
        MenuResponse menuResponse = new MenuResponse(
                menu,
                menuFoodResponses);

        return new ResponseEntity<>(menuResponse, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Create menu food",
            description = "Add more food for menu")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = MenuFoodBasicResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "menu-food/createNew")
    public ResponseEntity<?> createNewMenuFood(@Valid @RequestBody CreateMenuFoodRequest menuFoodRequest) {
        MenuFood menuFood = menuService.createNewMenuFood(menuFoodRequest);

        if (menuFood == null) {
            return new ResponseEntity<>(new MessageResponse("Create new menu food failed"), HttpStatus.BAD_REQUEST);
        }

        MenuFoodBasicResponse menuFoodResponse = new MenuFoodBasicResponse(
                menuFood.getMenuFoodID(),
                menuFood.getFood(),
                menuFood.getMealType(),
                menuFood.getIsActive());
        return new ResponseEntity<>(menuFoodResponse, HttpStatus.CREATED);

    }

    @Operation(
            summary = "Create menu food",
            description = "Add more food for menu")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = MenuFoodBasicResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "create-menu-foods")
    public ResponseEntity<?> createNewMenuFoods(@RequestBody List<CreateMenuFoodRequest> menuFoodRequests) {
        List<MenuFood> menuFoods = menuService.createNewMenuFoods(menuFoodRequests);

        if (menuFoods.isEmpty()) {
            return new ResponseEntity<>(new MessageResponse("Create new menu food failed"), HttpStatus.BAD_REQUEST);
        }
        List<MenuFoodBasicResponse> menuFoodResponses = new ArrayList<>();
        menuFoods.forEach(menuFood -> {

            MenuFoodBasicResponse menuFoodResponse = new MenuFoodBasicResponse(
                    menuFood.getMenuFoodID(),
                    menuFood.getFood(),
                    menuFood.getMealType(),
                    menuFood.getIsActive());
            menuFoodResponses.add(menuFoodResponse);
        });
        return new ResponseEntity<>(menuFoodResponses, HttpStatus.CREATED);

    }

    @Operation(
            summary = "Get all menu",
            description = "Get all menu include food")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MenuResponseAll.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "getAllMenu")
    //    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllMenu() {
        // Lấy danh sách menu
        Iterable<Menu> menus = menuService.findAll();

        // kiểm tra menu trống
        if (!menus.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo menu response
        List<MenuResponseAll> menuResponses = new ArrayList<>();

        menus.forEach(menu -> {
            menuResponses.add(new MenuResponseAll(menu));
        });

        return new ResponseEntity<>(menuResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get menu by advisor personally (ADVISOR) ",
            description = "Get all menu include food of advisor")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MenuResponseAll.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getMenuByAdvisor")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> getMenuByAdvisor() {
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

        // Lấy danh sách menu
        Iterable<Menu> menus = menuService.getAllByAdvisorID(advisor.getAdvisorID());
        if (!menus.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo menu response
        List<MenuResponseAll> menuResponses = new ArrayList<>();

        menus.forEach(menu -> {

            menuResponses.add(new MenuResponseAll(menu));
        });

        return new ResponseEntity<>(menuResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get menu available by advisor",
            description = "Get all menu of advisor with isActive true")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MenuResponseAll.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "advisor/get-available")
    public ResponseEntity<?> getMenuActiveByAdvisorID() {
        //get account from context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Tìm Advisor 
        Advisor advisor = advisorService.findByAccountID(principal.getId());
        if (advisor == null) {
            return new ResponseEntity<>(new MessageResponse("Cannot find advisor!"), HttpStatus.BAD_REQUEST);
        }

        // Lấy danh sách menu
        Iterable<Menu> menus = menuService.getMenuActiveByAdvisorID(advisor.getAdvisorID());

        if (!menus.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo menu response
        List<MenuResponseAll> menuResponses = new ArrayList<>();

        menus.forEach(menu -> {
            menuResponses.add(new MenuResponseAll(menu));
        });
        return new ResponseEntity<>(menuResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get by menu id",
            description = "Get all menu include food with menu id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MenuResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getMenuByID")
    public ResponseEntity<?> getMenuByID(@RequestParam Integer menuID) {

        // Tìm menu bằng menuID
        Optional<Menu> menu = menuService.findById(menuID);
        if (!menu.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo menu response
        List<MenuFoodResponse> menuFoodResponses = menuFoodService.getMenuFoodResponse(menu.get().getMenuID());
        MenuResponse menuResponses = new MenuResponse(
                menu.get(),
                menuFoodResponses);

        return new ResponseEntity<>(menuResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get details by menu id",
            description = "Get menu details include food with menu id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MenuResponseBasicFood.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/get-details")
    public ResponseEntity<?> getMenuDetails(@RequestParam Integer menuID) {

        // Tìm menu bằng menuID
        Optional<Menu> menu = menuService.findById(menuID);
        if (!menu.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo menu response
        MenuResponseBasicFood menuResponses = new MenuResponseBasicFood(
                menu.get(),
                menu.get().getMenuFoods());

        return new ResponseEntity<>(menuResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Update menu",
            description = "Update menu don't include deactivate, can update Mealtype of Meal food")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = MessageResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping(value = "/update")
    public ResponseEntity<?> updateMenu(@RequestBody UpdateMenuRequest menuRequest) {
        // tim menu 
        Menu menu = menuService.findById(menuRequest.getMenuID())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find menu with id{" + menuRequest.getMenuID() + "}!"));

        // cập nhật thông tin menu
        menu.update(menuRequest);
        menuService.save(menu);

        return new ResponseEntity<>(new MessageResponse("Update menu success"), HttpStatus.OK);
    }

    @Operation(
            summary = "Deactivate menu",
            description = "Deactivate menu by meuID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema(implementation = MessageResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/deactivate/{menuID}")
    public ResponseEntity<?> deactivateMenu(@PathVariable("menuID") int menuID) {
        Optional<Menu> menu = menuService.findById(menuID);

        if (menu.isPresent()) {
            menu.get().setIsActive(Boolean.FALSE);
            menuService.save(menu.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(new MessageResponse("Deactivate menu failed"), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Activate menu",
            description = "Activate menu by meuID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema(implementation = MessageResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping("/activate/{menuID}")
    public ResponseEntity<?> activateMenu(@PathVariable("menuID") int menuID) {
        Optional<Menu> menu = menuService.findById(menuID);

        if (menu.isPresent()) {
            menu.get().setIsActive(Boolean.TRUE);
            menuService.save(menu.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(new MessageResponse("Activate menu failed"), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Delete menu food",
            description = "Delete menu food by food id and menu id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema(implementation = MenuResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping(value = "menu-food/delete/{menuFoodID}")
    public ResponseEntity<?> delete(@PathVariable Integer menuFoodID) {
        menuFoodService.deleteByMenuFoodID(menuFoodID);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @Operation(
//            summary = "Deactivate menu food",
//            description = "Deactivate menu food by food id and menu id")
//    @ApiResponses({
//        @ApiResponse(responseCode = "204", content = {
//            @Content(schema = @Schema(implementation = MenuResponse.class), mediaType = "application/json")}),
//        @ApiResponse(responseCode = "403", content = {
//            @Content(schema = @Schema())}),
//        @ApiResponse(responseCode = "500", content = {
//            @Content(schema = @Schema())})})
//    @DeleteMapping(value = "/menu-food/deactivate")
//    public ResponseEntity<?> deactivateMenuFood(@RequestParam Integer menuFoodID) {
//        menuFoodService.deactivateMenuFood(menuFoodID);
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
    @Operation(
            summary = "Activate menu food",
            description = "Activate menu food by food id and menu id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema(implementation = MenuResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping(value = "/menu-food/activate")
    public ResponseEntity<?> activateMenuFood(@RequestParam Integer menuFoodID) {
        menuFoodService.activateMenuFood(menuFoodID);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @Operation(
//            summary = "Get menu by tag name",
//            description = "Get menu with tag name")
//    @ApiResponses({
//        @ApiResponse(responseCode = "200", content = {
//            @Content(schema = @Schema(implementation = MenuResponseAll.class), mediaType = "application/json")}),
//        @ApiResponse(responseCode = "403", content = {
//            @Content(schema = @Schema())}),
//        @ApiResponse(responseCode = "500", content = {
//            @Content(schema = @Schema())})})
//    @GetMapping(value = "/getMenuByTagName")
//    public ResponseEntity<?> getMenuSuggestion(@RequestParam String tagName) {
//        // gọi service lấy menu suggestion
//        List<Menu> menus = menuService.getMenuByTagName(tagName);
//
//        // kiểm tra kết quả
//        if (menus.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        // taoj menu response
//        List<MenuResponseAll> menuResponse = new ArrayList<>();
//
//        menus.forEach(menu -> {
//            menuResponse.add(new MenuResponseAll(menu));
//        });
//
//        return new ResponseEntity<>(menuResponse, HttpStatus.OK);
//    }
}
