/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.payload.response.AdvisorResponse;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.repository.AdvisorRepository;
import com.fu.bmi_tracker.services.AdvisorService;
import java.util.List;

@Service
public class AdvisorServiceImpl implements AdvisorService {

    @Autowired
    AdvisorRepository repository;

    @Override
    public Iterable<Advisor> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Advisor> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Advisor save(Advisor t) {
        return repository.save(t);
    }

    @Override
    public Advisor findByAccountID(Integer accountID) {
        return repository.findByAccountID(accountID).get();
    }

    @Override
    public List<AdvisorResponse> findAllAdvisorsWithDetails() {
        return repository.findAllAdvisorsWithDetails();
    }

}
