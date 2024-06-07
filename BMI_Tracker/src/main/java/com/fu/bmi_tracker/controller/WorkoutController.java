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
import com.fu.bmi_tracker.payload.request.UpdateWorkoutRequest;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.payload.response.WorkoutEntityResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @PostMapping(value = "/createNew")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> createNewWorkout(@Valid @RequestBody CreateWorkoutRequest createWorkoutRequest) {
        //get account from context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // find Advisor 
        Advisor advisor = advisorService.findByAccountID(principal.getId());
        if (advisor == null) {
            return new ResponseEntity<>(new MessageResponse("Cannot find advisor!"), HttpStatus.BAD_REQUEST);
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
        @ApiResponse(responseCode = "204", description = "Delete success!"),
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

    @Operation(
            summary = "Get all workout",
            description = "Get all workout")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Workout.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAllWorkout() {
        // Lấy danh sách menu
        Iterable<Workout> workouts = workoutService.findAll();

        // kiểm tra menu trống
        if (!workouts.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(workouts, HttpStatus.OK);
    }

    @Operation(
            summary = "Get workout by id",
            description = "Get workout by id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = WorkoutResonse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByID")
    public ResponseEntity<?> getWorkoutByID(@RequestParam Integer workoutID) {
        // Gọi service tim bằng workoutID
        Optional<Workout> workout = workoutService.findById(workoutID);
        if (!workout.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        // Tìm danh sách exercise
        List<Exercise> exercises = workoutExerciseService.getAllExerciseByWorkoutID(workoutID);

        WorkoutResonse resonse = new WorkoutResonse(workout.get(), exercises);

        return new ResponseEntity<>(resonse, HttpStatus.OK);

    }

    @Operation(
            summary = "Get workout by advisor id",
            description = "Get workout by advisor id")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Workout.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByAdvisorID")
    public ResponseEntity<?> getWorkoutByAdvisorID() {
        //get account from context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Tìm Advisor 
        Advisor advisor = advisorService.findByAccountID(principal.getId());
        if (advisor == null) {
            return new ResponseEntity<>(new MessageResponse("Cannot find advisor!"), HttpStatus.BAD_REQUEST);
        }
        // lấy danh sách workouts
        Iterable<Workout> workouts = workoutService.getWorkoutByAdvisorID(advisor.getAdvisorID());

        if (!workouts.iterator().hasNext()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(workouts, HttpStatus.OK);

    }

    @Operation(
            summary = "Update workout",
            description = "Update workout information not include WorkoutExercise")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = WorkoutEntityResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping(value = "/update")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> updateWorkoutInformation(@Valid @RequestBody UpdateWorkoutRequest updateWorkoutRequest) {
        // gọi workout service để cập nhật workout information
        Workout workout = workoutService.updateWorkoutInformation(updateWorkoutRequest);
        // tạo Workout entity response
        WorkoutEntityResponse entityResponse = new WorkoutEntityResponse(workout);
        return new ResponseEntity<>(entityResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete workout exercise",
            description = "Delete workout exercises by exercise ID and workoutID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Delete success!"),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping(value = "/deleteExersice")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> deleteWorkoutExersice(@RequestParam Integer workoutID, @RequestParam Integer exerciseID) {
        // gọi service để delete workout exercise
        workoutExerciseService.deleteWorkoutExercise(workoutID, exerciseID);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Create workout exercise",
            description = "Create workout exercises by exercise ID and workoutID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Delete success!"),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createWorkoutExercise")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> createWorkoutExercise(@RequestParam Integer workoutID, @RequestParam Integer exerciseID) {
        WorkoutExercise workoutExercise = workoutExerciseService.createWorkoutExercise(workoutID, exerciseID);

        return new ResponseEntity<>(workoutExercise, HttpStatus.OK);

    }

    @Operation(
            summary = "Create workout exercise",
            description = "Delete workout exercises by list exerciseID and workoutID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Delete success!"),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createWorkoutExercises")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> createWorkoutExercises(@RequestParam Integer workoutID, @RequestParam List<Integer> exerciseIDs) {
        // gọi service tạo workout exercise
        List<WorkoutExercise> workoutExercise = workoutExerciseService.createWorkoutExercises(workoutID, exerciseIDs);

        // kiểm tra danh sách rỗng
        if (workoutExercise.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
        return new ResponseEntity<>(workoutExercise, HttpStatus.OK);

    }
}
