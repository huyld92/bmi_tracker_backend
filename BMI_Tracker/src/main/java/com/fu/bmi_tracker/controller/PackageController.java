/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.Package;
import com.fu.bmi_tracker.model.enums.EPackageStatus;
import com.fu.bmi_tracker.payload.request.CreatePackageRequest;
import com.fu.bmi_tracker.payload.request.UpdatePackageRequest;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.payload.response.PackageAdvisorResponse;
import com.fu.bmi_tracker.payload.response.PackageResponse;
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
import com.fu.bmi_tracker.services.PackageService;

/**
 *
 * @author BaoLG
 */
@Tag(name = "Package", description = "Package management APIs")
@RestController
@RequestMapping("/api/packages")
public class PackageController {

    @Autowired
    PackageService packageService;

    @Operation(
            summary = "Create new Package with form (ADVISOR)",
            description = "Create new package with form")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = PackageAdvisorResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> createNewPackage(@RequestBody CreatePackageRequest createRequest) {
        // get account from context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        //Call service to save package
        Package p = packageService.createPackage(createRequest, principal.getId());

        //If fail to save new package
        if (p == null) {
            return new ResponseEntity<>(new MessageResponse(("Failed to create new package")), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //If success to save new package
        return new ResponseEntity<>(new PackageAdvisorResponse(packageService.save(p)), HttpStatus.CREATED);

    }

    @Operation(
            summary = "Get All Package",
            description = "Get All Package")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = PackageAdvisorResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAll")
    //@PreAuthorize("hasRole('ADVISOR')") MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getAllPackage() {

        Iterable<Package> packageList = packageService.findAll();

        if (!packageList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // tạo package response 
        List<PackageAdvisorResponse> packageResponses = new ArrayList<>();
        packageList.forEach(
                p -> {
                    packageResponses.add(new PackageAdvisorResponse(
                            p));
                }
        );
        return new ResponseEntity<>(packageResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Availble Package",
            description = "Get All Package that have is active")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = PackageAdvisorResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAllAvailable")
    public ResponseEntity<?> getAllAvaiblePackage() {

        Iterable<Package> packageList = packageService.findAllAvailablePackage();

        if (!packageList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // tạo package response 
        List<PackageAdvisorResponse> packageResponses = new ArrayList<>();
        packageList.forEach(
                p -> {
                    packageResponses.add(new PackageAdvisorResponse(
                            p));
                }
        );

        return new ResponseEntity<>(packageResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Package of an Advisor",
            description = "Get package list by specifying AdvisorID of its. The respone is a list of package of that Advisor")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = PackageAdvisorResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByAdvisorID/{id}")
    //@PreAuthorize("hasRole('ADVISOR')") MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getPackageByAdvisorID(@PathVariable("id") int id) {

        Iterable<Package> packageList = packageService.findAllPackageByAdvisorID(id);

        if (!packageList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // tạo package response
        List<PackageAdvisorResponse> packageResponses = new ArrayList<>();

        packageList.forEach(
                p -> {
                    packageResponses.add(new PackageAdvisorResponse(
                            p));
                }
        );

        return new ResponseEntity<>(packageResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Package of an Advisor",
            description = "Get package list by specifying AdvisorID of its. The respone is a list of package of that Advisor")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = PackageResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/get-for-subscription/{advisorID}")
    //@PreAuthorize("hasRole('ADVISOR')") MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getPackageForSubscription(@PathVariable("advisorID") Integer advisorID) {

        Iterable<Package> packageList = packageService.getAllPackageForSubscription(advisorID);

        if (!packageList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // tạo package response
        List<PackageResponse> packageResponses = new ArrayList<>();

        packageList.forEach(
                p -> {
                    packageResponses.add(new PackageResponse(
                            p));
                }
        );

        return new ResponseEntity<>(packageResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Receive all packages from the advisor personally (ADVISOR)",
            description = "Get package list by advisor personally if it's. The response is a list of package of that Advisor")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = PackageAdvisorResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByAdvisor")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> getPackageByAdvisor() {
        // lấy accountID từ context
        CustomAccountDetailsImpl princal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // gọi service tìm Package
        Iterable<Package> packageList = packageService.findAllPackageFromPersonally(princal.getId());

        if (!packageList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo package response 
        List<PackageAdvisorResponse> packageResponses = new ArrayList<>();

        packageList.forEach(
                p -> {
                    packageResponses.add(new PackageAdvisorResponse(
                            p));
                }
        );

        return new ResponseEntity<>(packageResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve a Package detials by id",
            description = "Get a Package object by specifying its id. The response is Package object")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = PackageAdvisorResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByID/{id}")
    //@PreAuthorize("hasRole('ADVISOR')") MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getByID(@PathVariable("id") int id) {
        Optional<Package> p = packageService.findById(id);

        if (p.isPresent()) {
            return new ResponseEntity<>(p, HttpStatus.OK
            );

        }

        // tạo package response
        PackageAdvisorResponse packageResponse = new PackageAdvisorResponse(
                p.get()
        );
        return new ResponseEntity<>(packageResponse, HttpStatus.OK);
    }

    @Operation(summary = "Update a Package by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = PackageAdvisorResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())})})
    @PutMapping("/update")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> updatePackage(@RequestBody UpdatePackageRequest updateRequest) {
        Optional<Package> p = packageService.findById(updateRequest.getPackageID());

        if (p.isPresent()) {

            p.get().setPackageName(updateRequest.getPackageName());
            p.get().setPrice(updateRequest.getPrice());
            p.get().setDescription(updateRequest.getDescription());
            p.get().setPackageDuration(updateRequest.getPackageDuration());
            return new ResponseEntity<>(new PackageAdvisorResponse(packageService.save(
                    p.get())), HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(new MessageResponse("Cannot find package with id{" + updateRequest.getPackageID() + "}"), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Deactivate a Package by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/deactivate/{packageID}")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> deactivatePackage(@PathVariable("packageID") int packageID) {
        Optional<Package> p = packageService.findById(packageID);

        if (p.isPresent()) {

            p.get().setIsActive(Boolean.FALSE);
            packageService.save(
                    p.get()
            );
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(new MessageResponse("Cannot find package with id{" + packageID + "}"), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Change status a Package by package Id (MANAGER)")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping("/change-package-status")
    public ResponseEntity<?> changePackage(@RequestParam Integer packageID, EPackageStatus packageStatus) {
        System.out.println("aaaaaaaaaa");
        Optional<Package> p = packageService.findById(packageID);
        if (p.isPresent()) {

            p.get().setPackageStatus(packageStatus.toString());
            packageService.save(
                    p.get()
            );
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(new MessageResponse("Cannot find package with id{" + packageID + "}"), HttpStatus.NOT_FOUND);
        }
    }

}
