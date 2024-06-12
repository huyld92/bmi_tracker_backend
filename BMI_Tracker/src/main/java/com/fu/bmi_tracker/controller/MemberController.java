/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.model.entities.ActivityLevel;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.Exercise;
import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.MemberBodyMass;
import com.fu.bmi_tracker.model.entities.Menu;
import com.fu.bmi_tracker.model.entities.Workout;
import com.fu.bmi_tracker.model.enums.EMealType;
import com.fu.bmi_tracker.payload.request.CreateMemberRequest;
import com.fu.bmi_tracker.payload.response.CreateMemberResponse;
import com.fu.bmi_tracker.payload.response.ExercisePageResponse;
import com.fu.bmi_tracker.payload.response.ExerciseResponse;
import com.fu.bmi_tracker.payload.response.FoodPageResponse;
import com.fu.bmi_tracker.payload.response.FoodResponse;
import com.fu.bmi_tracker.payload.response.MemberInformationResponse;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.services.ActivityLevelService;
import com.fu.bmi_tracker.services.MemberBodyMassService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fu.bmi_tracker.util.BMIUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.security.core.context.SecurityContextHolder;
import com.fu.bmi_tracker.services.MemberService;
import com.fu.bmi_tracker.services.MenuFoodService;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Member", description = "Member management APIs")
@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberBodyMassService memberBodyMassService;

    @Autowired
    ActivityLevelService activityLevelService;

    @Autowired
    MenuFoodService menuFoodService;

    @Autowired
    BMIUtils bMIUtils;

    @Operation(summary = "Create new member information", description = "Activity Level"
            + " Little to no exercise:1.2"
            + " Light exercise :1.375 "
            + "Moderate exercise:1.55 Hard exercise:1725"
            + "Very hard exercise:1.9")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = CreateMemberResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/createNew")
    public ResponseEntity<?> createNewMember(@Valid @RequestBody CreateMemberRequest createMemberRequest) {
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // Kiểm tra Account ID nếu tồn tại Member retunr badRequest
        if (memberService.existsByAccountID(principal.getId())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Member already exists!"));
        }
        // tính BMI
        double bmi = bMIUtils.calculateBMI(createMemberRequest.getWeight(), createMemberRequest.getHeight());

        // tính BMR
        int age = LocalDate.now().getYear() - principal.getBirthday().getYear();
        double bmr = bMIUtils.calculateBMR(createMemberRequest.getWeight(),
                createMemberRequest.getHeight(), age, principal.getGender());

        // tính tdee
        // tìm activity level
        ActivityLevel activityLevel = activityLevelService.findById(createMemberRequest.getActivityLevelID()).get();
        double tdee = bMIUtils.calculateTDEE(bmr, activityLevel.getActivityLevel());

        // calculateDefault default Calories
        int defaultCalories = bMIUtils.calculateDefaultCalories(tdee, createMemberRequest.getTargetWeight());

        LocalDateTime now = LocalDateTime.now(ZoneId.of("GMT+7"));

        // tìm menu suggestion theo dietatry preference và default calories
        Menu menu = memberService.getMenuSuggestion(defaultCalories, createMemberRequest.getDietaryPreference());

        // Tìm workout suggestion dựa trên BMI
        Workout workout = memberService.getWorkoutSuggestion(bMIUtils.classifyBMI(bmi));

        // Save member  Bổ sung menuID
        Member member = new Member(principal.getId(),
                createMemberRequest.getTargetWeight(),
                tdee,
                bmr,
                defaultCalories,
                false,
                now,
                createMemberRequest.getDietaryPreference(),
                new ActivityLevel(createMemberRequest.getActivityLevelID()),
                //Thay đổi menuDI and WorkoutID
                menu.getMenuID(),
                workout.getWorkoutID()
        );

        Member memberSaved = memberService.save(member);

        // Save member body mass
        MemberBodyMass bodyMass = new MemberBodyMass(createMemberRequest.getHeight(),
                createMemberRequest.getWeight(),
                age, bmi,
                now, memberSaved);

        memberBodyMassService.save(bodyMass);

        // Generate suggestion menu
        CreateMemberResponse createMemberResponse = new CreateMemberResponse(principal.getId(), defaultCalories,
                createMemberRequest.getHeight(), createMemberRequest.getWeight(), age, bmi, bmr, tdee);

        return new ResponseEntity<>(createMemberResponse, HttpStatus.CREATED);
    }
 
    @Operation(
            summary = "Retrieve All Food in menu by meal type (MEMBER)",
            description = "Member send meal type to get food list")
    @ApiResponses({
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = FoodResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getMenuByMealType")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getMemberMenuByMealType(@RequestParam EMealType mealType) {
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        // Find member by accountID
        Optional<Member> member = memberService.findByAccountID(principal.getId());

        if (!member.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Member already exists!"));
        }

        // call service find food
        List<Food> foods = menuFoodService.findFoodByMenu_MenuIDAndMealType(member.get().getMenuID(), mealType);

        //check food list 
        if (foods.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (foods.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<FoodResponse> foodResponses = new ArrayList<>();
        foods.forEach(food -> {
            FoodResponse foodResponse = new FoodResponse(
                    food.getFoodID(), food.getFoodName(),
                    food.getFoodCalories(),
                    food.getDescription(),
                    food.getFoodPhoto(),
                    food.getFoodVideo(),
                    food.getFoodNutrition(),
                    food.getFoodTimeProcess(),
                    food.getCreationDate(),
                    food.getIsActive());
            foodResponses.add(foodResponse);
        });
        return new ResponseEntity<>(foodResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Assign menu for member (ADVISOR)",
            description = "Create new menu with form")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Menu.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PutMapping(value = "/assignMenu")
    @PreAuthorize("hasRole('AVISOR')")
    public ResponseEntity<?> assignMenu(@Valid @RequestParam Integer menuID, Integer memberID) {
        // find member by member id
        Optional<Member> member = memberService.findById(memberID);
        // check existed
        if (!member.isPresent()) {
            return new ResponseEntity<>(new MessageResponse("Cannot find member with id {" + memberID + "}"), HttpStatus.BAD_REQUEST);
        }
        // set menuID
        member.get().setMenuID(menuID);
        //Update member
        memberService.save(member.get());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve All Food in menu (MEMBER)",
            description = "Member get food list")
    @ApiResponses({
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = FoodResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getMenu")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getAllFoodOfMemberByMenuID() {
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // Find member by accountID
        Optional<Member> member = memberService.findByAccountID(principal.getId());

        if (!member.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Member already exists!"));
        }

        // call service find food
        List<Food> foods = menuFoodService.findFoodByMenu_MenuID(member.get().getMenuID());

        //check food list 
        if (foods.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<FoodResponse> foodResponses = new ArrayList<>();
        foods.forEach(food -> {
            FoodResponse foodResponse = new FoodResponse(
                    food.getFoodID(), food.getFoodName(),
                    food.getFoodCalories(), food.getDescription(), food.getFoodPhoto(),
                    food.getFoodVideo(), food.getFoodNutrition(),
                    food.getFoodTimeProcess(),
                    food.getCreationDate(),
                    food.getIsActive());
            foodResponses.add(foodResponse);
        });

        return new ResponseEntity<>(foodResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve member information (MEMBER)",
            description = "Member get personal information")
    @ApiResponses({
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = MemberInformationResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/getMemberInformation")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getMemberInformation() {
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // Find member by accountID
        Optional<Member> member = memberService.findByAccountID(principal.getId());

        if (!member.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Member not exists!"));
        }

        MemberBodyMass bodyMass
                = memberBodyMassService.getLatestBodyMass(
                        member.get().getMemberID());

        MemberInformationResponse memberInformationResponse;
        if (bodyMass != null) {
            memberInformationResponse = new MemberInformationResponse(
                    member.get().getMemberID(),
                    principal.getEmail(),
                    principal.getFullName(),
                    principal.getGender().toString(),
                    principal.getPhoneNumber(),
                    bodyMass.getHeight(),
                    bodyMass.getWeight(),
                    bodyMass.getAge(),
                    bodyMass.getBmi(),
                    member.get().getBmr(),
                    member.get().getTdee());
        } else {
            memberInformationResponse = new MemberInformationResponse(
                    member.get().getMemberID(),
                    principal.getEmail(),
                    principal.getFullName(),
                    principal.getGender().toString(),
                    principal.getPhoneNumber(),
                    0,
                    0,
                    0,
                    0,
                    member.get().getBmr(),
                    member.get().getTdee());
        }

        return new ResponseEntity<>(memberInformationResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve All exercise in workout (MEMBER)",
            description = "Member get exercises list in workout")
    @ApiResponses({
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = ExerciseResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/workout/getAllExercise")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getAllExerciseOfMemberByWorkoutID() {
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // gọi service tìm exercises của workout
        List<Exercise> exercises = memberService.getllExerciseResponseInWorkout(principal.getId());

        // kiểm tra kết quả nếu empty trả về 204
        if (exercises.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo exercises response
        List<ExerciseResponse> exerciseResponses = new ArrayList<>();

        // chuyển đổi từ list exercise thành list exercise response
        exercises.forEach(exercise -> {
            exerciseResponses.add(new ExerciseResponse(exercise));
        });

        return new ResponseEntity<>(exerciseResponses, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve All foods with paging (MEMBER)",
            description = "Get a list of foods with paging, prioritizing the dietary preferences of the member.")
    @ApiResponses({
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = FoodPageResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/foods/getPriority")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getAllFoodWithpriorityTagName(Pageable pageable) {
        // Lấy account ID từ context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // gọi service tìm danh sách food và phân trang
        Page<Food> page = memberService.getPaginatedFoodWithPriority(principal.getId(), pageable);

        // chuyển dổi từ food sang food response
        List<FoodResponse> foodResponses = page.get()
                .map(food -> {
                    return new FoodResponse(food);
                })
                .collect(Collectors.toList());

        // tạo FoodPage Response
        FoodPageResponse foodPageResponse = new FoodPageResponse(
                foodResponses,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.getNumberOfElements()
        );

        return new ResponseEntity<>(foodPageResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve all exercise with paging (MEMBER)",
            description = "Get a list of exercises with paging, prioritizing the BMI status of the member.")
    @ApiResponses({
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = ExercisePageResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/exercises/getPriority")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getAllExerciseWithpriorityTagName(Pageable pageable) {
        // Lấy account ID từ context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // gọi service tìm danh sách exercise đã phân trang
        Page<Exercise> page = memberService.getPaginatedExerciseWithPriority(principal.getId(), pageable);

        // chuyển dổi từ exercise sang exercise response
        List<ExerciseResponse> exerciseResponses = page.get()
                .map(exercise -> {
                    return new ExerciseResponse(exercise);
                })
                .collect(Collectors.toList());

        // tạo Exercise Page Response
        ExercisePageResponse exercisePageResponse = new ExercisePageResponse(
                exerciseResponses,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.getNumberOfElements()
        );

        return new ResponseEntity<>(exercisePageResponse, HttpStatus.OK);

    }
}
