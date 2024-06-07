/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Plan;
import com.fu.bmi_tracker.payload.request.CreatePlanRequest;
import com.fu.bmi_tracker.payload.request.UpdatePlanRequest;
import com.fu.bmi_tracker.services.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author BaoLG
 */
@Tag(name = "Plan", description = "Plan management APIs")
@RestController
@RequestMapping("/api/test/plans")
public class PlanController {
    
    @Autowired
    PlanService planService;
    
    @Operation(
            summary = "Create new Plan with form",
            description = "Create new plan with form")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Plan.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> createNewPlan(@RequestBody CreatePlanRequest createRequest) {
        
        //Convert createRequest to Plan before save
        Plan newPlan = new Plan();
        newPlan.setPlanName(createRequest.getPlanName());
        newPlan.setPrice(createRequest.getPrice());
        newPlan.setDescription(createRequest.getDescription());
        newPlan.setPlanDuration(createRequest.getPlanDuration());
        newPlan.setAdvisorID(createRequest.getAdvisorID());
        newPlan.setActive(Boolean.TRUE);
        
        //Call service to save plan
        Plan planSave = planService.save(newPlan);
        
        //If fail to save new plan
        if (planSave == null) {
            return new ResponseEntity<>("Failed to create new plan", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        //If success to save new plan
        return new ResponseEntity<>("Plan is successfully created", HttpStatus.CREATED);

    }
    
    @Operation(
            summary = "Get All Plan",
            description = "Get All Plan")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Plan.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAll")
    //@PreAuthorize("hasRole('ADVISOR')") MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getAllPlan() {
        
        Iterable<Plan> planList = planService.findAll();
        
        if (!planList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
       
        return new ResponseEntity<>(planList, HttpStatus.OK);
    }
    
    @Operation(
            summary = "Get All Availble Plan",
            description = "Get All Plan that have is active")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Plan.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAllAvaible")
    //@PreAuthorize("hasRole('ADVISOR')") MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getAllAvaiblePlan() {
        
        Iterable<Plan> planList = planService.findAllAvailblePlan();
        
        if (!planList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
       
        return new ResponseEntity<>(planList, HttpStatus.OK);
    }
    
    @Operation(
            summary = "Get All Plan of an Advisor",
            description = "Get plan list by specifying AdvisorID of its. The respone is a list of plan of that Advisor")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Plan.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByAdvisorID/{id}")
    //@PreAuthorize("hasRole('ADVISOR')") MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getPlanByAdvisorID(@PathVariable("id") int id) {
        
        Iterable<Plan> planList = planService.findAllPlanByAdvisorID(id);
        
        if (!planList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
       
        return new ResponseEntity<>(planList, HttpStatus.OK);
    }
    
    @Operation(
            summary = "Retrieve a Plan detials by id",
            description = "Get a Plan object by specifying its id. The response is Plan object")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Plan.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByID/{id}")
    //@PreAuthorize("hasRole('ADVISOR')") MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getByID(@PathVariable("id") int id) {
        
        Optional<Plan> plan = planService.findById(id);
        
        if (plan.isPresent()) {
            return new ResponseEntity<>(plan, HttpStatus.OK);
            
        }  
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a Plan by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Plan.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())})})
    @PutMapping("/update")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> updatePlan(@RequestBody UpdatePlanRequest updateRequest) {
        Optional<Plan> plan = planService.findById(updateRequest.getPlanID());
        
        if (plan.isPresent()) {
            plan.get().setPlanName(updateRequest.getPlanName());
            plan.get().setPrice(updateRequest.getPrice());
            plan.get().setDescription(updateRequest.getDescription());
            plan.get().setPlanDuration(updateRequest.getPlanDuration());
            return new ResponseEntity<>(planService.save(plan.get()), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Cannot find plan with id{" + updateRequest.getPlanID() + "}", HttpStatus.NOT_FOUND);
        }
    }
    
    @Operation(summary = "Deactive a Plan by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/deactive/{id}")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> deactivePlan(@PathVariable("id") int id) {
        Optional<Plan> plan =  planService.findById(id);

        if (plan.isPresent()) {
            plan.get().setActive(Boolean.FALSE);
            return new ResponseEntity<>(planService.save(plan.get()), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Cannot find plan with id{" + id + "}", HttpStatus.NOT_FOUND);
        }
    }
}
