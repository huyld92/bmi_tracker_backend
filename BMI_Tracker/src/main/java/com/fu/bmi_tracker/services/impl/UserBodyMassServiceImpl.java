/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.UserBodyMass;
import com.fu.bmi_tracker.repository.UserBodyMassRepository;
import com.fu.bmi_tracker.services.UserBodyMassService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBodyMassServiceImpl implements UserBodyMassService {

    @Autowired
    UserBodyMassRepository repository;

    @Override
    public Iterable<UserBodyMass> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<UserBodyMass> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public UserBodyMass save(UserBodyMass t) {
        return repository.save(t);
    }

}
