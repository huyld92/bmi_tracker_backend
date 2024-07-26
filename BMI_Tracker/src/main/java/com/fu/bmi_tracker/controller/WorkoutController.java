/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.Workout;
import com.fu.bmi_tracker.model.entities.WorkoutExercise;
import com.fu.bmi_tracker.payload.request.CreateWorkoutExerciseRequest;
import com.fu.bmi_tracker.payload.request.CreateWorkoutRequest;
import com.fu.bmi_tracker.payload.request.UpdateWorkoutRequest;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.payload.response.WorkoutEntityResponse;
import com.fu.bmi_tracker.payload.response.WorkoutExerciseResponse;
import com.fu.bmi_tracker.payload.response.WorkoutResonse;
import com.fu.bmi_tracker.services.AdvisorService;
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
import com.fu.bmi_tracker.util.WorkoutExerciseConverter;
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
    WorkoutExerciseService workoutExerciseService;

    @Operation(
            summary = "Create new workout (ADVISOR)",
            description = "Create new workout with form")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = WorkoutResonse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> createNewWorkout(@Valid @RequestBody CreateWorkoutRequest createWorkoutRequest) {
        //Lấy thông tin login từ context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // gọi service create new workout
        Workout workoutSaved = workoutService.createNewWorkout(createWorkoutRequest, principal.getId());

        if (workoutSaved == null) {
            return new ResponseEntity<>(new MessageResponse("Cannot create new workout!"), HttpStatus.BAD_REQUEST);
        }

        // tạo Workout response
        WorkoutEntityResponse workoutEntityResponse = new WorkoutEntityResponse(
                workoutSaved);

        return new ResponseEntity<>(workoutEntityResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Deactivate a Workout by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Deactivate success!"),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateWorkout(@PathVariable("id") int id) {
        Optional<Workout> workout = workoutService.findById(id);

        if (workout.isPresent()) {
            workout.get().setIsActive(Boolean.FALSE);
            workoutService.save(workout.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Cannot find workout with id{" + id + "}", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Activate a Workout by Id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Activate success!"),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping("/activate")
    public ResponseEntity<?> activateWorkout(@RequestParam int workoutID) {
        Optional<Workout> workout = workoutService.findById(workoutID);

        if (workout.isPresent()) {
            workout.get().setIsActive(Boolean.TRUE);
            workoutService.save(workout.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Cannot find workout with id{" + workoutID + "}", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Get all workout",
            description = "Get all workout")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = WorkoutResonse.class), mediaType = "application/json")}),
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

        // tạo workout response
        List<WorkoutResonse> workoutResonses = new ArrayList<>();

        // duyệt list workout
        workouts.forEach(workout -> {
            workoutResonses.add(new WorkoutResonse(
                    workout,
                    WorkoutExerciseConverter.convertToWorkoutExerciseResponseList(
                            workout.getWorkoutExercises())));
        });

        return new ResponseEntity<>(workoutResonses, HttpStatus.OK);
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

        //tạo workoutExercise response
        List<WorkoutExerciseResponse> workoutExercisesResponses = new ArrayList<>();

        workout.get().getWorkoutExercises().forEach(workoutExercise -> {
            workoutExercisesResponses.add(new WorkoutExerciseResponse(workoutExercise));
        });

        // tạo workout response
        WorkoutResonse response = new WorkoutResonse(
                workout.get(),
                workoutExercisesResponses);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Get workout by advisor (ADVISOR)",
            description = "Get workout by advisor ")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = WorkoutEntityResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getByAdvisor")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> getWorkoutByAdvisor() {
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

        // tạo workout response
        List<WorkoutEntityResponse> workoutResonses = new ArrayList<>();
        // duyeejt list workout
        workouts.forEach(workout -> {
            workoutResonses.add(new WorkoutEntityResponse(workout));
        });

        return new ResponseEntity<>(workoutResonses, HttpStatus.OK);

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
    @DeleteMapping(value = "/workout-exercise/delete")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> deleteWorkoutExersice(@RequestParam Integer workoutExerciseID) {
        // gọi service để delete workout exercise
        workoutExerciseService.deleteWorkoutExercise(workoutExerciseID);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Deactivate workout exercise",
            description = "Deactivate workout exercises by exercise ID and workoutID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Delete success!"),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @DeleteMapping(value = "/workout-exercise/deactivate")
//    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> deactivateWorkoutExersice(@RequestParam Integer workoutExerciseID) {
        // gọi service để deactivate workout exercise
        workoutExerciseService.deactivateWorkoutExercise(workoutExerciseID);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Activate workout exercise",
            description = "Activate workout exercises by exercise ID and workoutID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Activate success!"),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping(value = "/workout-exercise/activate")
//    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> activateWorkoutExersice(@RequestParam Integer workoutExerciseID) {
        // gọi service để activate workout exercise
        workoutExerciseService.activateWorkoutExercise(workoutExerciseID);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Create workout exercise")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Creatate success!", content = {
            @Content(schema = @Schema(implementation = MessageResponse.class))
        }),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createWorkoutExercises")
    @PreAuthorize("hasRole('ADVISOR')")
    public ResponseEntity<?> createWorkoutExercise(@Valid @RequestBody CreateWorkoutExerciseRequest workoutExerciseRequest) {
        // gọi service tạo workout exercise
        WorkoutExercise workoutExercise = workoutExerciseService.createWorkoutExercise(workoutExerciseRequest);

        // kiểm tra danh sách rỗng
        if (workoutExercise == null) {
            return new ResponseEntity<>(new MessageResponse("Create workout exercise failed"), HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity<>(new MessageResponse("Create workout exercise success"), HttpStatus.CREATED);

    }
    
}
