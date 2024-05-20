/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Account;
import com.fu.bmi_tracker.payload.response.AccountResponse;
import com.fu.bmi_tracker.repository.AccountRepository;
import com.fu.bmi_tracker.services.AccountService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository repository;

    @Override
    public Iterable<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Account> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Account save(Account t) {
        return repository.save(t);
    }

    @Override
    public List<AccountResponse> findAllAccountResponse() {
        return repository.findAllAccountResponse();
    }

}
