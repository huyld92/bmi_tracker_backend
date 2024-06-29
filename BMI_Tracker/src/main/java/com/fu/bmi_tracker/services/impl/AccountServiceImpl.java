/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Account;
import com.fu.bmi_tracker.model.entities.Role;
import com.fu.bmi_tracker.payload.request.CreateAccountRequest;
import com.fu.bmi_tracker.payload.request.UpdateProfileRequest;
import com.fu.bmi_tracker.repository.AccountRepository;
import com.fu.bmi_tracker.repository.RoleRepository;
import com.fu.bmi_tracker.services.AccountService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

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

        // set Roles
        Set<Role> accountRoles = new HashSet<>();
        accountRoles.add(roleRepository.findByRoleName(createAccountRequest.getRole()));

        account.setRoles(new HashSet<>(accountRoles));

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
    public void addMoreRole(Integer accountID, Integer roleID) {
        // tìm account bằng accountID
        Account account = accountRepository.findById(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find acount!"));

        // tìm role bằng role ID
        Role role = roleRepository.findById(roleID)
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

}
