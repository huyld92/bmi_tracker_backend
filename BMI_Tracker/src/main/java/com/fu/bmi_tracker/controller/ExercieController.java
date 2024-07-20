/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Exercise;
import com.fu.bmi_tracker.payload.request.CreateExerciseRequest;
import com.fu.bmi_tracker.payload.request.UpdateExerciseRequest;
import com.fu.bmi_tracker.payload.response.ExerciseResponse;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.services.ExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@Tag(name = "Exercise", description = "Exercise management APIs")
@RestController
@RequestMapping("/api/exercises")
public class ExercieController {

    @Autowired
    ExerciseService exerciseService;

    @Operation(summary = "Create new exercise with form", description = "Create new exercise with form")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = ExerciseResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewExercise(@Valid @RequestBody CreateExerciseRequest createExerciseRequest) {
        // gọi service tạo exercise từ CreateExerciseRequest
        Exercise exercise = exerciseService.createExercise(createExerciseRequest);

        // kiểm tra kết quả
        if (exercise == null) {
            return new ResponseEntity<>(new MessageResponse("Error create new exercise!"), HttpStatus.BAD_REQUEST);
        }
        // tạo Exercise Response
        ExerciseResponse exerciseEntityResponse = new ExerciseResponse(exercise);

        return new ResponseEntity<>(exerciseEntityResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Update exercise", description = "Update exercise  information not include Tag")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ExerciseResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping(value = "/update")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateExercise(@Valid @RequestBody UpdateExerciseRequest updateExercerRequest) {
        // gọi service update exercise từ updateExercerRequest
        Exercise exercise = exerciseService.updateExercise(updateExercerRequest);

        // kiểm tra kết quả
        if (exercise == null) {
            return new ResponseEntity<>(new MessageResponse("Error update exercise!"), HttpStatus.BAD_REQUEST);
        }
        // tạo ExerciseResponse
        ExerciseResponse exerciseResponse = new ExerciseResponse(exercise);
        return new ResponseEntity<>(exerciseResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get all exercise", description = "Get all exercise information include Tag")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ExerciseResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAll")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getALLExercise() {
        // gọi service get all exercise
        Iterable<Exercise> exercises = exerciseService.findAll();

        // kiểm tra kết quả
        if (!exercises.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo Exercise response
        List<ExerciseResponse> exerciseResponses = new ArrayList<>();

        // chuyển đổi từ Exercise sang ExerciseResponse
        exercises.forEach(exercise -> {
            // tạo Exercise entity response
            ExerciseResponse response = new ExerciseResponse(
                    exercise);

            exerciseResponses.add(response);
        });

        return new ResponseEntity<>(exerciseResponses, HttpStatus.OK);
    }

    @Operation(summary = "Get exercise by id", description = "Get exercise information include Tag")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ExerciseResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByID")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getByExerciseID(@RequestParam Integer exerciseID) {
        // gọi service get exercise by id
        Optional<Exercise> exercise = exerciseService.findById(exerciseID);

        // kiểm tra kết quả
        if (!exercise.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo Exercise entity response
        ExerciseResponse response = new ExerciseResponse(exercise.get());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Deactivate exercise ", description = "Deactivate exercise by id")
    @ApiResponses({
        @ApiResponse(responseCode = "204"),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping(value = "/deactivate/{exerciseID}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deactivateExercise(@PathVariable(value = "exerciseID") Integer exerciseID) {
        // gọi service deactivate exercise
        exerciseService.deactivateExercise(exerciseID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
