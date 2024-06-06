/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Menu;
import com.fu.bmi_tracker.services.MenuService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.repository.MenuRepository;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository repostory;

    @Override
    public Iterable<Menu> findAll() {
        return repostory.findAll();
    }

    @Override
    public Optional<Menu> findById(Integer id) {
        return repostory.findById(id);
    }

    @Override
    public Menu save(Menu t) {
        return repostory.save(t);
    }

    @Override
    public Iterable<Menu> getAllByAdvisorID(Integer advisorID) {
        return repostory.findByAdvisorID(advisorID);
    }

}
