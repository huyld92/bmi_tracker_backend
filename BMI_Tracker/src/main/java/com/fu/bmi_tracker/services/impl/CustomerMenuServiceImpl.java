/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.CustomerMenu;
import com.fu.bmi_tracker.repository.CustomerMenuRepository;
import com.fu.bmi_tracker.services.CustomerMenuService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CustomerMenuServiceImpl implements CustomerMenuService {
    
    @Autowired
    CustomerMenuRepository repository;
    
    @Override
    public Iterable<CustomerMenu> findAll() {
        return repository.findAll();
    }
    
    @Override
    public Optional<CustomerMenu> findById(Integer id) {
        return repository.findById(id);
    }
    
    @Override
    public CustomerMenu save(CustomerMenu t) {
        return repository.save(t);
    }
    
}
