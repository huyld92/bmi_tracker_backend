/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.controller;

import com.fu.bmi_tracker.exceptions.TokenException;
import com.fu.bmi_tracker.payload.request.LoginRequest;
import com.fu.bmi_tracker.payload.request.RegisterRequest;
import com.fu.bmi_tracker.payload.response.LoginResponse;
import com.fu.bmi_tracker.payload.response.MessageResponse;
import com.fu.bmi_tracker.model.entities.Account;
import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.Role;
import com.fu.bmi_tracker.model.enums.ERole;
import com.fu.bmi_tracker.security.jwt.JwtUtils;
import com.fu.bmi_tracker.model.entities.CustomAccountDetailsImpl;
import com.fu.bmi_tracker.model.entities.EmailDetails;
import com.fu.bmi_tracker.model.entities.EmailVerificationCode;
import com.fu.bmi_tracker.model.entities.RefreshToken;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.MemberBodyMass;
import com.fu.bmi_tracker.payload.request.TokenRefreshRequest;
import com.fu.bmi_tracker.payload.response.AccountResponse;
import com.fu.bmi_tracker.payload.response.LoginForMemberResponse;
import com.fu.bmi_tracker.payload.response.TokenRefreshResponse;
import com.fu.bmi_tracker.services.AccountService;
import com.fu.bmi_tracker.services.AdvisorService;
import com.fu.bmi_tracker.services.EmailService;
import com.fu.bmi_tracker.services.EmailVerificationCodeService;
import com.fu.bmi_tracker.services.MemberBodyMassService;
import com.fu.bmi_tracker.services.RefreshTokenService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fu.bmi_tracker.services.MemberService;
import com.fu.bmi_tracker.services.RoleService;
import com.fu.bmi_tracker.util.BMIUtils;
import com.fu.bmi_tracker.util.PasswordUtils;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Duc Huy
 */
@Tag(name = "Authentication", description = "Authentication management APIs")
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailVerificationCodeService verificationCodeService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountService accountService;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    MemberService memberService;

    @Autowired
    AdvisorService advisorService;

    @Autowired
    MemberBodyMassService memberBodyMassService;

    @Operation(summary = "login by phone number and password", description = "Authenticate accounts by phone number and password. Returned will be account information and will not include a password", tags = {
        "Authentication"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = LoginResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "400", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "401", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                        loginRequest.getPassword()));
        // Authenticated
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // generate mã jwt
        String jwt = jwtUtils.generateJwtToken(loginRequest.getEmail());

        CustomAccountDetailsImpl accountDetails = (CustomAccountDetailsImpl) authentication.getPrincipal();

        // Lấy danh sách quyền của người dùng
        List<String> roles = authentication.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // kiểm tra role mà người dùng muốn truy cập có hơp lệ
        boolean isRole = roles.contains(loginRequest.getRole().toString());

        if (!isRole) {
            return new ResponseEntity<>(new MessageResponse("You do not have permission for this role."), HttpStatus.FORBIDDEN);
        }

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(accountDetails.getId());

        // nếu đăng nhập với role Advisor 
        if (loginRequest.getRole() == ERole.ROLE_ADVISOR) {
            // kiểm tra thông tin advisor
            Advisor advisor = advisorService.findByAccountID(accountDetails.getId());
            // nếu trạng thía advisor inactive response 202 
            if (!advisor.getIsActive()) {
                LoginResponse loginResponse = new LoginResponse(
                        accountDetails.getId(),
                        accountDetails.getEmail(),
                        accountDetails.getAccountPhoto(),
                        loginRequest.getRole(),
                        refreshToken.getRefreshToken(),
                        jwt);
                return new ResponseEntity<>(loginResponse, HttpStatus.ACCEPTED);
            }
        } else if (loginRequest.getRole() == ERole.ROLE_MEMBER) {
            Optional<Member> member = memberService.findByAccountID(accountDetails.getId());

            if (!member.isPresent()) {
                LoginForMemberResponse forMemberResponse = new LoginForMemberResponse(
                        accountDetails.getId(),
                        -1, accountDetails.getEmail(),
                        accountDetails.getFullName(),
                        accountDetails.getGender().toString(),
                        accountDetails.getPhoneNumber(),
                        -1, -1, -1, -1, -1, -1,
                        refreshToken.getRefreshToken(),
                        jwt);

                return new ResponseEntity<>(forMemberResponse, HttpStatus.ACCEPTED);
            }
            MemberBodyMass bodyMass
                    = memberBodyMassService.getLatestBodyMass(
                            member.get().getMemberID());

            // tính tuổi
            int age = LocalDate.now().getYear() - member.get().getAccount().getBirthday().getYear();

            // tính BMI
            BMIUtils bMIUtils = new BMIUtils();
            double bmi = bMIUtils.calculateBMI(bodyMass.getWeight(), bodyMass.getHeight());

            // tính BMR
            double bmr = bMIUtils.calculateBMR(
                    bodyMass.getWeight(),
                    bodyMass.getHeight(),
                    age,
                    member.get().getAccount().getGender());

            LoginForMemberResponse memberResponse = new LoginForMemberResponse(
                    accountDetails.getId(),
                    member.get().getMemberID(),
                    accountDetails.getEmail(),
                    accountDetails.getFullName(),
                    accountDetails.getGender().toString(),
                    accountDetails.getPhoneNumber(),
                    bodyMass.getHeight(),
                    bodyMass.getWeight(),
                    age,
                    bmi,
                    bmr,
                    member.get().getTdee(),
                    refreshToken.getRefreshToken(),
                    jwt);

            return new ResponseEntity<>(memberResponse, HttpStatus.OK);
        }

        return ResponseEntity.ok(new LoginResponse(
                accountDetails.getId(),
                accountDetails.getEmail(),
                accountDetails.getAccountPhoto(),
                loginRequest.getRole(),
                refreshToken.getRefreshToken(),
                jwt));
    }

    @Operation(summary = "Login for member by phone number and password", description = "Authenticate accounts by phone number and password. Returned will member information", tags = {
        "Authentication"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = LoginForMemberResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "202", content = {
            @Content(schema = @Schema(description = "Empty member information!"))}),
        @ApiResponse(responseCode = "400", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "401", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping("/loginMember")
    public ResponseEntity<?> loginForMember(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomAccountDetailsImpl accountDetails = (CustomAccountDetailsImpl) authentication.getPrincipal();

        // Lấy danh sách quyền của người dùng
        List<String> roles = authentication.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // kiểm tra role mà người dùng muốn truy cập có hơp lệ
        boolean isRole = roles.contains(loginRequest.getRole().toString());

        if (!isRole) {
            return new ResponseEntity<>(new MessageResponse("You do not have permission for this role."), HttpStatus.UNAUTHORIZED);
        }

        Optional<Member> member = memberService.findByAccountID(accountDetails.getId());

        // generate mã jwt
        String jwt = jwtUtils.generateJwtToken(loginRequest.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(accountDetails.getId());

        if (!member.isPresent()) {
            LoginForMemberResponse forMemberResponse = new LoginForMemberResponse(
                    accountDetails.getId(),
                    -1, accountDetails.getEmail(),
                    accountDetails.getFullName(),
                    accountDetails.getGender().toString(),
                    accountDetails.getPhoneNumber(),
                    -1, -1, -1, -1, -1, -1,
                    refreshToken.getRefreshToken(),
                    jwt);

            return new ResponseEntity<>(forMemberResponse, HttpStatus.ACCEPTED);

        }
        MemberBodyMass bodyMass
                = memberBodyMassService.getLatestBodyMass(
                        member.get().getMemberID());

        // tính tuổi
        int age = LocalDate.now().getYear() - member.get().getAccount().getBirthday().getYear();
        // tính BMI
        BMIUtils bMIUtils = new BMIUtils();
        double bmi = bMIUtils.calculateBMI(bodyMass.getWeight(), bodyMass.getHeight());

        // tính BMR
        double bmr = bMIUtils.calculateBMR(
                bodyMass.getWeight(),
                bodyMass.getHeight(),
                age,
                member.get().getAccount().getGender());

        LoginForMemberResponse forMemberResponse = new LoginForMemberResponse(
                accountDetails.getId(),
                member.get().getMemberID(),
                accountDetails.getEmail(),
                accountDetails.getFullName(),
                accountDetails.getGender().toString(),
                accountDetails.getPhoneNumber(),
                bodyMass.getHeight(),
                bodyMass.getWeight(),
                age,
                bmi,
                bmr,
                member.get().getTdee(),
                refreshToken.getRefreshToken(),
                jwt);
        return ResponseEntity.ok(forMemberResponse);
    }

    @Operation(summary = "Register", description = "Register account with default role member", tags = {
        "Authentication"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = AccountResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "401", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@Valid @RequestBody RegisterRequest registerRequest) throws Exception {

        if (accountService.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

//        if (accountService.existsByPhoneNumber(registerRequest.getPhoneNumber())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Phone number is already taken!"));
//        }
        // tìm role bằng role name
        Role role = roleService.findByRoleName(ERole.ROLE_MEMBER)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find role!"));
        // set Role
        Set<Role> accountRoles = new HashSet<>();
        accountRoles.add(role);

        // Create new member's account
        Account account = new Account(registerRequest.getFullName(),
                registerRequest.getEmail(),
                registerRequest.getPhoneNumber(),
                encoder.encode(
                        registerRequest.getPassword()),
                registerRequest.getGender(),
                registerRequest.getBirthday(),
                accountRoles);

        // Save thông tin account xuống database
        accountService.save(account);

        // Create New User in firebase
        try {
            UserRecord.CreateRequest createRequest = new CreateRequest();
            createRequest.setEmail(registerRequest.getEmail());
            createRequest.setEmailVerified(false);
            createRequest.setPassword(registerRequest.getPassword());

            // Creating new user
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(createRequest);

            //Generate Veritification Link
            String link = FirebaseAuth.getInstance().generateEmailVerificationLink(registerRequest.getEmail());

            EmailVerificationCode verificationCode = new EmailVerificationCode(link, registerRequest.getEmail());

            verificationCodeService.save(verificationCode);

            //Send mail with vertificaiton link
            EmailDetails details = new EmailDetails(userRecord.getEmail(), link, registerRequest.getFullName());
            emailService.sendSimpleMail(details);
        } catch (FirebaseAuthException e) {
            System.out.println("Error with firebase account creation");
        }
        AccountResponse accountResponse = new AccountResponse(account);
        return ResponseEntity.ok(accountResponse);

    }

    @Operation(summary = "Register as advisor", description = "Register account with default role advisor", tags = {
        "Authentication"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = AccountResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "401", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping("/register-as-advisor")
    public ResponseEntity<?> registerWithAdvisor(@Valid @RequestBody RegisterRequest registerRequest) throws Exception {

        if (accountService.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

//        if (accountService.existsByPhoneNumber(registerRequest.getPhoneNumber())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Phone number is already taken!"));
//        }
        // tìm role bằng role name
        Role role = roleService.findByRoleName(ERole.ROLE_ADVISOR)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find role!"));

        // set Role
        Set<Role> accountRoles = new HashSet<>();

        accountRoles.add(role);

        // Create new member's account
        Account account = new Account(registerRequest.getFullName(),
                registerRequest.getEmail(),
                registerRequest.getPhoneNumber(),
                encoder.encode(
                        registerRequest.getPassword()),
                registerRequest.getGender(),
                registerRequest.getBirthday(),
                accountRoles);

        // Save thông tin account xuống database
        Account accountSave = accountService.save(account);

        Advisor advisor = new Advisor(
                accountSave,
                "",
                "",
                0,
                false
        );
        advisorService.save(advisor);

        // Create New User in firebase
        try {
            UserRecord.CreateRequest createRequest = new CreateRequest();
            createRequest.setEmail(registerRequest.getEmail());
            createRequest.setEmailVerified(false);
            createRequest.setPassword(registerRequest.getPassword());

            // Creating new user
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(createRequest);

            //Generate Veritification Link
            String link = FirebaseAuth.getInstance().generateEmailVerificationLink(registerRequest.getEmail());

            EmailVerificationCode verificationCode = new EmailVerificationCode(link, registerRequest.getEmail());

            verificationCodeService.save(verificationCode);

            //Send mail with vertificaiton link
            EmailDetails details = new EmailDetails(userRecord.getEmail(), link, registerRequest.getFullName());
            emailService.sendSimpleMail(details);
        } catch (FirebaseAuthException e) {
            System.out.println("Error with firebase account creation");
        }

        AccountResponse accountResponse = new AccountResponse(account);

        return ResponseEntity.ok(accountResponse);
    }

    @Operation(summary = "Refresh token", description = "Using refresh token to get new access token when expired", tags = {
        "Authentication"})
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = TokenRefreshResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "401", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        String requestRefreshToken = tokenRefreshRequest.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(refreshToken -> refreshToken.getAccount())
                .map(account -> {
                    String token = jwtUtils.generateJwtToken(account.getEmail());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenException(requestRefreshToken,
                "Refresh token is not in database!"));
    }

    @Operation(summary = "Log out", description = "Log out of the system", tags = {"Authentication"})
    @ApiResponses({
        @ApiResponse(responseCode = "200"),
        @ApiResponse(responseCode = "404", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "401", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        CustomAccountDetailsImpl accountDetails = (CustomAccountDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if (accountDetails != null) {
            // lấy account id từ context
            Integer accountID = accountDetails.getId();
            // gọi refreshTokenService xóa refreshtoken
            refreshTokenService.deleteByAccountID(accountID);

            // gọi accountService xóa deviceToken
            accountService.updateDeviceToken(accountID, null);
        }

        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }

    @Operation(summary = "Forgot password",
            description = "Provide email to receive new password")
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = MessageResponse.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", content = {
            @Content(schema = @Schema())}),
        @ApiResponse(responseCode = "500", content = {
            @Content(schema = @Schema())})})
    @PostMapping(value = "/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        // Generate default password and endcode to save
        PasswordUtils passwordUtils = new PasswordUtils();

        String defaultPassword = passwordUtils.generateRandomPassword(10);

        //tìm account bằng email
        Account account = accountService.findByEmail(email);

        // kiểm tra trạng thái active
        if (!account.getIsActive()) {
            return new ResponseEntity<>(new MessageResponse("Your account is banned!"), HttpStatus.CREATED);
        }

        // gửi mail mật khẩu mới về email
        EmailDetails details = new EmailDetails();
        details.setRecipient(account.getEmail());
        details.setSubject("Your New Password");
        String msgBody = details.sendResetPasswordTemplateGenerator(email, defaultPassword, account.getFullName());
        details.setMsgBody(msgBody);
        emailService.sendSimpleMail(details);
        // cập nhật mật khẩu mới
        account.setPassword(encoder.encode(defaultPassword));
        accountService.save(account);
        return new ResponseEntity<>(new MessageResponse("We've sent new passsword to your mail"), HttpStatus.OK);
    }
}
