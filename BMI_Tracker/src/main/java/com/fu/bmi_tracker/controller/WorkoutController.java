/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.Exercise;
import com.fu.bmi_tracker.model.entities.Workout;
import com.fu.bmi_tracker.model.entities.WorkoutExercise;
import com.fu.bmi_tracker.payload.request.CreateWorkoutRequest;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.payload.response.WorkoutResonse;
import com.fu.bmi_tracker.services.AdvisorService;
import com.fu.bmi_tracker.services.ExerciseService;
import com.fu.bmi_tracker.services.WorkoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fu.bmi_tracker.services.WorkoutExerciseService;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Workout", description = "Workout management APIs")
@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    @Autowired
    WorkoutService workoutService;

    @Autowired
    AdvisorService advisorService;

    @Autowired
    ExerciseService exerciseService;

    @Autowired
    WorkoutExerciseService workoutExerciseService;

    @Operation(
            summary = "Create new workout",
            description = "Create new workout with form")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = WorkoutResonse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping(value = "/createNew")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> createNewWorkout(@Valid @RequestBody CreateWorkoutRequest createWorkoutRequest) {
        //get account from context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // find Advisor 
        Advisor advisor = advisorService.findByAccountID(principal.getId());
        if (advisor == null) {
            return new ResponseEntity<>(new MessageResponse("Cannot find trainer!"), HttpStatus.BAD_REQUEST);
        }

        // Store workout
        Workout w = new Workout(
                createWorkoutRequest.getWorkoutName(),
                createWorkoutRequest.getWorkoutDescription(),
                createWorkoutRequest.getTotalCaloriesBurned(),
                advisor.getAdvisorID());

        Workout workoutSaved = workoutService.save(w);

        if (workoutSaved == null) {
            return new ResponseEntity<>(new MessageResponse("Cannot create new workout!"), HttpStatus.BAD_REQUEST);
        }

        // Create workout exercise
        // Find list exercise
        List<Exercise> exercises = exerciseService.findByExerciseIDIn(createWorkoutRequest.getExerciseIDs());

        List<WorkoutExercise> workoutExercises = new ArrayList<>();

        // Create Workout Exercise from list exercise
        exercises.forEach(exercise -> {
            workoutExercises.add(new WorkoutExercise(workoutSaved, exercise));
        });

        // store Workout Exercise
        if (workoutExerciseService.saveAll(workoutExercises).isEmpty()) {
            return new ResponseEntity<>("Failed to create new workout exercise", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Create workout response
        WorkoutResonse workoutResonse = new WorkoutResonse(workoutSaved, exercises);

        return new ResponseEntity<>(workoutResonse, HttpStatus.OK);
    }

    @Operation(summary = "Deactive a Workout by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/deactive/{id}")
    public ResponseEntity<?> deactiveWorkout(@PathVariable("id") int id) {
        Optional<Workout> workout = workoutService.findById(id);

        if (workout.isPresent()) {
            workout.get().setIsActive(Boolean.FALSE);
            return new ResponseEntity<>(workoutService.save(workout.get()), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Cannot find food with id{" + id + "}", HttpStatus.NOT_FOUND);
        }
    }
}
