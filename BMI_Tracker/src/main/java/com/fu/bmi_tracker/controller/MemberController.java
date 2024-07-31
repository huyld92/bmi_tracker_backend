/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.exceptions.ErrorMessage;
import com.fu.bmi_tracker.model.entities.Account;
import com.fu.bmi_tracker.model.entities.ActivityLevel;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.DailyRecord;
import com.fu.bmi_tracker.model.entities.Exercise;
import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.MemberBodyMass;
import com.fu.bmi_tracker.model.entities.WorkoutExercise;
import com.fu.bmi_tracker.model.enums.EMealType;
import com.fu.bmi_tracker.payload.request.CreateMemberRequest;
import com.fu.bmi_tracker.payload.response.CreateMemberResponse;
import com.fu.bmi_tracker.payload.response.ExercisePageResponse;
import com.fu.bmi_tracker.payload.response.ExerciseResponse;
import com.fu.bmi_tracker.payload.response.FoodPageResponse;
import com.fu.bmi_tracker.payload.response.FoodResponse;
import com.fu.bmi_tracker.payload.response.MemberInformationResponse;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.payload.response.WorkoutExerciseResponse;
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
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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

        // Save member  Bổ sung menuID
        Member member = new Member(
                new Account(principal.getId()),
                createMemberRequest.getTargetWeight(),
                tdee,
                defaultCalories,
                false,
                now,
                createMemberRequest.getDietaryPreference(),
                new ActivityLevel(createMemberRequest.getActivityLevelID())
        );

        Member memberSaved = memberService.save(member);

        // Save member body mass
        MemberBodyMass bodyMass = new MemberBodyMass(
                createMemberRequest.getHeight(),
                createMemberRequest.getWeight(),
                now, memberSaved);

        memberBodyMassService.save(bodyMass);

        // Generate suggestion menu
        CreateMemberResponse createMemberResponse = new CreateMemberResponse(memberSaved.getMemberID(), defaultCalories,
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

        // gọi memberService tìm Food trong menu của advisor theo 
        List<Food> foods = memberService.getFoodsInMenuByMealType(principal.getId(), mealType);

        //check food list 
        if (foods.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<FoodResponse> foodResponses = new ArrayList<>();

        foods.forEach(food -> {
            FoodResponse foodResponse = new FoodResponse(food);
            foodResponses.add(foodResponse);
        });
        return new ResponseEntity<>(foodResponses, HttpStatus.OK);
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

        // gọi service tìm tất cả food có trong menu hiện tại của member
        List<Food> foods = memberService.getAllFoodInMenu(principal.getId());

        //check food list 
        if (foods.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<FoodResponse> foodResponses = new ArrayList<>();
        foods.forEach(food -> {
            FoodResponse foodResponse = new FoodResponse(food);
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

        double bmi = bMIUtils.calculateBMI(bodyMass.getWeight(), bodyMass.getHeight());

        int age = LocalDate.now().getYear() - member.get().getAccount().getBirthday().getYear();

        double bmr = bMIUtils.calculateBMR(bodyMass.getWeight(), bodyMass.getHeight(), age, principal.getGender());

        memberInformationResponse = new MemberInformationResponse(
                member.get().getMemberID(),
                principal.getEmail(),
                principal.getAccountPhoto(),
                principal.getFullName(),
                principal.getGender().toString(),
                principal.getPhoneNumber(),
                member.get().getEndDateOfPlan(),
                member.get().getAccount().getBirthday(),
                bodyMass.getHeight(),
                bodyMass.getWeight(),
                member.get().getTargetWeight(),
                age,
                bmi,
                bmr,
                member.get().getTdee());

        return new ResponseEntity<>(memberInformationResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve member information by member id",
            description = "Member get personal information")
    @ApiResponses({
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = MemberInformationResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/get-information")
    public ResponseEntity<?> getMemberInformationByID(@RequestParam Integer memberID) {

        // Find member by accountID
        Optional<Member> member = memberService.findById(memberID);

        if (!member.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: member with id{" + memberID + "} not exists!"));
        }

        MemberBodyMass bodyMass
                = memberBodyMassService.getLatestBodyMass(
                        member.get().getMemberID());

        MemberInformationResponse memberInformationResponse;

        double bmi = bMIUtils.calculateBMI(bodyMass.getWeight(), bodyMass.getHeight());

        int age = LocalDate.now().getYear() - member.get().getAccount().getBirthday().getYear();

        double bmr = bMIUtils.calculateBMR(
                bodyMass.getWeight(),
                bodyMass.getHeight(),
                age,
                member.get().getAccount().getGender());

        memberInformationResponse = new MemberInformationResponse(
                member.get().getMemberID(),
                member.get().getAccount().getEmail(),
                member.get().getAccount().getAccountPhoto(),
                member.get().getAccount().getFullName(),
                member.get().getAccount().getGender().toString(),
                member.get().getAccount().getPhoneNumber(),
                member.get().getEndDateOfPlan(),
                member.get().getAccount().getBirthday(),
                bodyMass.getHeight(),
                bodyMass.getWeight(),
                member.get().getTargetWeight(),
                age,
                bmi,
                bmr,
                member.get().getTdee());

        return new ResponseEntity<>(memberInformationResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Retrieve all workout exercise in workout (MEMBER)",
            description = "Member get workout exercises list in workout")
    @ApiResponses({
        @ApiResponse(responseCode = "200",
                content = {
                    @Content(schema = @Schema(implementation = WorkoutExerciseResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping(value = "/workout-exercise/getAll")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getAllWorkoutExerciseOfMemberByWorkoutID() {
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // gọi service tìm exercises của workout
        List<WorkoutExercise> workoutExercises = memberService.getAllWorkoutExerciseInWorkout(principal.getId());

        // kiểm tra kết quả nếu empty trả về 204
        if (workoutExercises.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // tạo exercises response
        List<WorkoutExerciseResponse> exerciseResponses = new ArrayList<>();

        // chuyển đổi từ list exercise thành list exercise response
        workoutExercises.forEach(workoutExercise -> {
            exerciseResponses.add(new WorkoutExerciseResponse(workoutExercise));
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
    public ResponseEntity<?> getAllFoodWithpriorityTagName(Pageable pageable, @RequestParam(required = false) List<Integer> tagIDs) {
        Page<Food> page;

        // nếu tagIDs có giá trị thì gọi service để filter
        if (tagIDs == null) {
            // Lấy account ID từ context
            CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            page = memberService.getPaginatedFoodWithPriority(principal.getId(), pageable);

        } else {
            page = memberService.getPaginatedFoodFilterTag(pageable, tagIDs);
        }

        // gọi service tìm danh sách food và phân trang
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
    public ResponseEntity<?> getAllExerciseWithpriorityTagName(Pageable pageable, @RequestParam(required = false) Integer tagID) {
        // Lấy account ID từ context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Page<Exercise> page;
        if (tagID == null || tagID < 0) {
            // gọi service tìm danh sách exercise đã phân trang
            page = memberService.getPaginatedExerciseWithPriority(principal.getId(), pageable);
        } else {
            // nếu tagID > 0 kèm didefu kiện khi get Exercise
            page = memberService.getPaginatedExerciseFilterTag(tagID, pageable);
        }
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

    // Lấy danh sách daily record trong một ngày 
    @Operation(summary = "Retrieve daily record of date (MEMBER)",
            description = "Get daily record for a date")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = DailyRecord.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "204", description = "There are no meal", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @GetMapping("/dailyrecord/getByDate")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<?> getByDate(@RequestParam String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate;
        // Validation date 
        try {
            localDate = LocalDate.parse(date, formatter);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), "Invalid date format. Please provide the date in the format yyyy-MM-dd.", "");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        // tìm account id từ context
        CustomAccountDetailsImpl principal = (CustomAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Tìm daily record theo ngày của current account
        DailyRecord dailyRecord = memberService.getDailyRecordOfMember(principal.getId(), localDate);

        return new ResponseEntity<>(dailyRecord, HttpStatus.OK);
    }

}
