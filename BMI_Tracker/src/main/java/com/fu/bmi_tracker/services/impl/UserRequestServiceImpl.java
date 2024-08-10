/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.UserRequest;
import com.fu.bmi_tracker.repository.UserRequestRepository;
import com.fu.bmi_tracker.services.UserRequestService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRequestServiceImpl implements UserRequestService {

    @Autowired
    UserRequestRepository userRequestRepository;

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

}
