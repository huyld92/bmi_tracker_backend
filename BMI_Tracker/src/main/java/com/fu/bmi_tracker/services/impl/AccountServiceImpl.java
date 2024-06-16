/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Account;
import com.fu.bmi_tracker.model.entities.Role;
import com.fu.bmi_tracker.payload.request.CreateAccountRequest;
import com.fu.bmi_tracker.repository.AccountRepository;
import com.fu.bmi_tracker.repository.RoleRepository;
import com.fu.bmi_tracker.services.AccountService;
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
    
}
