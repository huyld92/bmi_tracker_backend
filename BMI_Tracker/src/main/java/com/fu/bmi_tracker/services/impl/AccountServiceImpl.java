/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.exceptions.DuplicateRecordException;
import com.fu.bmi_tracker.model.entities.Account;
import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.Role;
import com.fu.bmi_tracker.model.enums.ERole;
import com.fu.bmi_tracker.payload.request.CreateAccountRequest;
import com.fu.bmi_tracker.payload.request.UpdateAdvisorProfileRequest;
import com.fu.bmi_tracker.payload.request.UpdateProfileRequest;
import com.fu.bmi_tracker.repository.AccountRepository;
import com.fu.bmi_tracker.repository.AdvisorRepository;
import com.fu.bmi_tracker.repository.RoleRepository;
import com.fu.bmi_tracker.services.AccountService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AdvisorRepository advisorRepository;

    @Override
    public Iterable<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> findById(Integer id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account save(Account t) {
        return accountRepository.save(t);
    }

    @Override
    public Account createAccount(CreateAccountRequest createAccountRequest, String password) {
        // Create new object
        Account account = new Account(createAccountRequest);

        if (accountRepository.existsByEmail(createAccountRequest.getEmail())) {
            throw new DuplicateRecordException("Email already exists");
        }
        // gọi repository tìm Role bằng roleName
        Role role = roleRepository.findByRoleName(createAccountRequest.getRole())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find role!"));

        // set Roles
        Set<Role> accountRoles = new HashSet<>();
        accountRoles.add(role);

        account.setRoles(accountRoles);

        account.setPassword(password);

        return accountRepository.save(account);
    }

    @Override
    public void updateDeviceToken(Integer accountID, String deviceToken) {
        // update deviceToken
        accountRepository.updateDeviceToken(accountID, deviceToken);
    }

    @Override
    public void updateProfile(Integer accountID, UpdateProfileRequest updateProfileRequest) {
        // tìm account bằng accountID
        Account account = findById(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find acount!"));
        account.setAccountPhoto(updateProfileRequest.getAccountPhoto());
        account.setFullName(updateProfileRequest.getFullName());
        account.setPhoneNumber(updateProfileRequest.getPhoneNumber());
        account.setGender(updateProfileRequest.getGender());
        account.setBirthday(updateProfileRequest.getBirthday());

        // cập nhật account xuống database
        save(account);
    }

    @Override
    public void addMoreRole(Integer accountID, ERole roleName) {
        // tìm account bằng accountID
        Account account = accountRepository.findById(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find acount!"));

        // tìm role bằng role ID
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find role!"));

        // thêm role mới
        account.getRoles().add(role);
        // cập nhật lại account
        save(account);

    }

    @Override
    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return accountRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public void updateAccountPhoto(Integer accountID, String imageLink) {
        // update account photo
        accountRepository.updateAccountPhoto(accountID, imageLink);
    }

    @Override
    public void deleteRole(Integer accountID, ERole roleName) {
        // tìm account bằng account ID
        Account account = accountRepository.findById(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find acount!"));

        // remove role trong account
        Set<Role> roles
                = account.getRoles().stream() // Sử dụng stream để lọc các vai trò mà không có roleName cần xóa
                        .filter(role -> !role.getRoleName().equals(roleName))// Giữ lại các vai trò không có roleName này
                        .collect(Collectors.toSet());  // collect các vai trò còn lại vào một tập hợp mới

        // set lại roles cho account
        account.setRoles(roles);

        // cập nhật lại thông tin account
        save(account);
    }

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find acount with email {" + email + "}!"));

    }

    @Override
    public void updateAdvisorProfile(Integer accountID, UpdateAdvisorProfileRequest updateProfileRequest) {
        // tìm account bằng accountID
        Account account = findById(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find acount!"));
        account.setFullName(updateProfileRequest.getFullName());
        account.setPhoneNumber(updateProfileRequest.getPhoneNumber());
        account.setGender(updateProfileRequest.getGender());
        account.setBirthday(updateProfileRequest.getBirthday());

        //tìm thông tin advisor 
        Advisor advisor
                = advisorRepository.findByAccount_AccountID(accountID)
                        .orElseThrow(() -> new EntityNotFoundException("Cannot find advisor with account id{" + accountID + "}!"));

        //cập nhật banknumber
        if (!advisor.getBankNumber().equals(updateProfileRequest.getBankNumber())) {
            advisor.setBankNumber(updateProfileRequest.getBankNumber());
            advisorRepository.save(advisor);
        }
        // cập nhật account xuống database
        save(account);

    }

    @Override
    public String findDeviceTokenByAccountID(Integer accountID) {
        return accountRepository.findDeviceTokenByAccountID(accountID);
    }

}
