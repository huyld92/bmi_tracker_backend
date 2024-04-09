/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Trainer;
import com.fu.bmi_tracker.repository.TrainerRepository;
import com.fu.bmi_tracker.services.TrainerService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    TrainerRepository repository;

    @Override
    public Iterable<Trainer> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Trainer> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Trainer save(Trainer t) {
        return repository.save(t);
    }

    @Override
    public Trainer findByAccountID(Integer accountID) {
        return repository.findByAccountID(accountID);
    }

}
