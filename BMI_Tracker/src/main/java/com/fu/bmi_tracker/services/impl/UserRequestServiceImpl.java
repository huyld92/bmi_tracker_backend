/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Account;
import com.fu.bmi_tracker.model.entities.UserRequest;
import com.fu.bmi_tracker.model.enums.EStatus;
import com.fu.bmi_tracker.payload.request.CreateUserRequest;
import com.fu.bmi_tracker.payload.request.UpdateUserRequestProcessing;
import com.fu.bmi_tracker.repository.AccountRepository;
import com.fu.bmi_tracker.repository.UserRequestRepository;
import com.fu.bmi_tracker.services.UserRequestService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRequestServiceImpl implements UserRequestService {

    @Autowired
    UserRequestRepository userRequestRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Iterable<UserRequest> findAll() {
        return userRequestRepository.findAll();
    }

    @Override
    public Optional<UserRequest> findById(Integer id) {
        return userRequestRepository.findById(id);
    }

    @Override
    public UserRequest save(UserRequest t) {
        return userRequestRepository.save(t);
    }

    @Override
    public Iterable<UserRequest> findByAccountID(Integer accountID) {
        return userRequestRepository.findByAccount_AccountID(accountID);

    }

    @Override
    public UserRequest createNewUserRequest(CreateUserRequest createUserRequest, Integer id) {
        // tìm account
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        UserRequest userRequest = new UserRequest(
                createUserRequest.getType(),
                createUserRequest.getPurpose(),
                EStatus.PENDING.toString(),
                LocalDate.now(),
                null,
                null,
                account);

        return save(userRequest);
    }

    @Override
    public UserRequest updateProcessing(UpdateUserRequestProcessing userRequestProcessing) {
        // tìm user request
        UserRequest userRequest = userRequestRepository.findById(userRequestProcessing.getUserRequestID())
                .orElseThrow(() -> new EntityNotFoundException("User Request with id{" + userRequestProcessing.getUserRequestID() + "} not found"));

        // cập nhật processing
        userRequest.setProcessNote(userRequestProcessing.getProcessNote());
        userRequest.setProcessingDate(LocalDate.now());
        userRequest.setStatus(userRequestProcessing.getStatus().toString());
        return save(userRequest);
    }

}
