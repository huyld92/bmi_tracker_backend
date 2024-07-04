/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Advisor;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.repository.AdvisorRepository;
import com.fu.bmi_tracker.services.AdvisorService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AdvisorServiceImpl implements AdvisorService {

    @Autowired
    AdvisorRepository advisorRepository;

    @Override
    public Iterable<Advisor> findAll() {
        return advisorRepository.findAll();
    }

    @Override
    public Optional<Advisor> findById(Integer id) {
        return advisorRepository.findById(id);
    }

    @Override
    public Advisor save(Advisor t) {
        return advisorRepository.save(t);
    }

    @Override
    public Advisor findByAccountID(Integer accountID) {
        return advisorRepository.findByAccount_AccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find advisor with account id{" + accountID + "}!"));

    }

    @Override
    public List<Advisor> findAllAdvisorsWithDetails() {
        return advisorRepository.findAllByIsActiveTrue();
    }

}
