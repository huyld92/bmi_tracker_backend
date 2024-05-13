/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Transaction;
import com.fu.bmi_tracker.repository.TransactionRepository;
import com.fu.bmi_tracker.services.TransactionService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository repository;

    @Override
    public Iterable<Transaction> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Transaction> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Transaction save(Transaction t) {
        return repository.save(t);
    }

}
