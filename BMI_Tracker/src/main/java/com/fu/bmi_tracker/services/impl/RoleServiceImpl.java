/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Role;
import com.fu.bmi_tracker.model.enums.ERole;
import com.fu.bmi_tracker.repository.RoleRepository;
import com.fu.bmi_tracker.services.RoleService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Override
    public Iterable<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Role save(Role t) {
        return repository.save(t);
    }

    @Override
    public Optional<Role> findByRoleName(ERole eRole) {
        return repository.findByRoleName(eRole);
    }

}
