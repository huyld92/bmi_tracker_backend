/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.Plan;
import com.fu.bmi_tracker.model.enums.EPlanStatus;
import com.fu.bmi_tracker.payload.request.CreatePlanRequest;
import com.fu.bmi_tracker.payload.request.UpdatePlanRequest;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.payload.response.PlanAdvisorResponse;
import com.fu.bmi_tracker.payload.response.PlanResponse;
import com.fu.bmi_tracker.services.PlanService;
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

/**
 *
 * @author BaoLG
 */
@Tag(name = "Plan", description = "Plan management APIs")
@RestController
@RequestMapping("/api/plans")
public class PlanController {

    @Autowired
    PlanService planService;

    @Operation(
            summary = "Create new Plan with form (ADVISOR)",
            description = "Create new plan with form")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = PlanAdvisorResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> createNewPlan(@RequestBody CreatePlanRequest createRequest) {
        // get account from context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        //Call service to save plan
        Plan plan = planService.createPlan(createRequest, principal.getId());

        //If fail to save new plan
        if (plan == null) {
            return new ResponseEntity<>(new MessageResponse(("Failed to create new plan")), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //If success to save new plan
        return new ResponseEntity<>(new PlanAdvisorResponse(planService.save(plan)), HttpStatus.CREATED);

    }

    @Operation(
            summary = "Get All Plan",
            description = "Get All Plan")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = PlanAdvisorResponse.class), mediaType = "application/json")}),
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
        // tạo plan response 
        List<PlanAdvisorResponse> planResponses = new ArrayList<>();
        planList.forEach(plan -> {
            planResponses.add(new PlanAdvisorResponse(plan));
        });
        return new ResponseEntity<>(planResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Availble Plan",
            description = "Get All Plan that have is active")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = PlanAdvisorResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAllAvailable")
    public ResponseEntity<?> getAllAvaiblePlan() {

        Iterable<Plan> planList = planService.findAllAvailablePlan();

        if (!planList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // tạo plan response 
        List<PlanAdvisorResponse> planResponses = new ArrayList<>();
        planList.forEach(plan -> {
            planResponses.add(new PlanAdvisorResponse(plan));
        });

        return new ResponseEntity<>(planResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Plan of an Advisor",
            description = "Get plan list by specifying AdvisorID of its. The respone is a list of plan of that Advisor")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = PlanAdvisorResponse.class), mediaType = "application/json")}),
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
        // tạo plan response
        List<PlanAdvisorResponse> planResponses = new ArrayList<>();

        planList.forEach(plan -> {
            planResponses.add(new PlanAdvisorResponse(plan));
        });

        return new ResponseEntity<>(planResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Plan of an Advisor",
            description = "Get plan list by specifying AdvisorID of its. The respone is a list of plan of that Advisor")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = PlanResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/get-for-subscription/{advisorID}")
    //@PreAuthorize("hasRole('ADVISOR')") MEMBER, ADVISOR, STAFF
    public ResponseEntity<?> getPlanForSubscription(@PathVariable("advisorID") Integer advisorID) {

        Iterable<Plan> planList = planService.getAllPlanForSubscription(advisorID);

        if (!planList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // tạo plan response
        List<PlanResponse> planResponses = new ArrayList<>();

        planList.forEach(plan -> {
            planResponses.add(new PlanResponse(plan));
        });

        return new ResponseEntity<>(planResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Receive all plans from the advisor personally (ADVISOR)",
            description = "Get plan list by advisor personally if it's. The response is a list of plan of that Advisor")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = PlanAdvisorResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByAdvisor")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> getPlanByAdvisor() {
        // lấy accountID từ context
        CustomAccountDetailsImpl princal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // gọi service tìm Plan
        Iterable<Plan> planList = planService.findAllPlanFromPersonally(princal.getId());

        if (!planList.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo plan response 
        List<PlanAdvisorResponse> planResponses = new ArrayList<>();

        planList.forEach(plan -> {
            planResponses.add(new PlanAdvisorResponse(plan));
        });

        return new ResponseEntity<>(planResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve a Plan detials by id",
            description = "Get a Plan object by specifying its id. The response is Plan object")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = PlanAdvisorResponse.class), mediaType = "application/json")}),
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

        // tạo plan response
        PlanAdvisorResponse planResponse = new PlanAdvisorResponse(plan.get());
        return new ResponseEntity<>(planResponse, HttpStatus.OK);
    }

    @Operation(summary = "Update a Plan by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = PlanAdvisorResponse.class), mediaType = "application/json")}),
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

            return new ResponseEntity<>(new PlanAdvisorResponse(planService.save(plan.get())), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageResponse("Cannot find plan with id{" + updateRequest.getPlanID() + "}"), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Deactivate a Plan by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/deactivate/{planID}")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> deactivatePlan(@PathVariable("planID") int planID) {
        Optional<Plan> plan = planService.findById(planID);

        if (plan.isPresent()) {
            plan.get().setIsActive(Boolean.FALSE);
            planService.save(plan.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(new MessageResponse("Cannot find plan with id{" + planID + "}"), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Change status a Plan by plan Id (MANAGER)")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping("/change-plan-status")
    public ResponseEntity<?> changePlan(@RequestParam Integer planID, EPlanStatus planStatus) {
        System.out.println("aaaaaaaaaa");
        Optional<Plan> plan = planService.findById(planID);
        if (plan.isPresent()) {
            plan.get().setPlanStatus(planStatus.toString());
            planService.save(plan.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(new MessageResponse("Cannot find plan with id{" + planID + "}"), HttpStatus.NOT_FOUND);
        }
    }

}
