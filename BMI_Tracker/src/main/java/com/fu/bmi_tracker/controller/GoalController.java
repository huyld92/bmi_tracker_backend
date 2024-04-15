/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Goal;
import com.fu.bmi_tracker.payload.request.CreateGoalRequest;
import com.fu.bmi_tracker.services.GoalService;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Goal", description = "Goal management APIs")
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/test/goals")
public class GoalController {

    @Autowired
    GoalService service;

    @Operation(
            summary = "Create new goal with form",
            description = "Create new goal with form: include MultiplePath for goal photo",
            tags = {"Goal"})
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Goal.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    public ResponseEntity<?> createNewGoal(@RequestBody CreateGoalRequest createGoalRequest) {
        Goal goal = new Goal(createGoalRequest);

        Goal goalResponse = service.save(goal);

        return new ResponseEntity<>(goalResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve all Goals", tags = {"Goal"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Goal.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no Goals", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllGoals() {

        Iterable goals = service.findAll();

        if (!goals.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(goals, HttpStatus.OK);

    }

    @Operation(
            summary = "Retrieve a Goal by Id",
            description = "Get a Goal object by specifying its id. The response is Goal object",
            tags = {"Goal"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Goal.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/getByID/{id}")
    public ResponseEntity<?> getGoalById(@PathVariable("id") int id) {
        Optional<Goal> goal = service.findById(id);

        if (goal.isPresent()) {
            return new ResponseEntity<>(goal, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find goal with id{" + id + "}", HttpStatus.NOT_FOUND);
        }
    }

//    @Operation(summary = "Update a Goal by Id", tags = {"Goal"})
//    @ApiResponses({
//        @ApiResponse(responseCode = "200", content = {
//            @Content(schema = @Schema(implementation = Goal.class), mediaType = "application/json")}),
//        @ApiResponse(responseCode = "500", content = {
//            @Content(schema = @Schema())}),
//        @ApiResponse(responseCode = "404", content = {
//            @Content(schema = @Schema())})})
//    @PutMapping("/update")
//    public ResponseEntity<?> updateGoal(@RequestBody Goal goalRequest) {
//        Optional<Goal> goal = service.findById(goalRequest.getGoalID());
//
//        if (goal.isPresent()) {
//            goal.get().update(goalRequest);
//            return new ResponseEntity<>(service.save(goal.get()), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Cannot find goal with id{" + goalRequest.getGoalID() + "}", HttpStatus.NOT_FOUND);
//        }
//    }
    @Operation(summary = "Delete a Goal by Id", tags = {"Goal"})
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteGoal(@PathVariable("id") int id) {
        Optional<Goal> goal = service.findById(id);

        if (goal.isPresent()) {
            goal.get().setStatus("InActive");
            return new ResponseEntity<>(service.save(goal.get()), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Cannot find goal with id{" + id + "}", HttpStatus.NOT_FOUND);
        }
    }
}
