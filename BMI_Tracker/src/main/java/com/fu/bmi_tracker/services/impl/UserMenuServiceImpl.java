/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.UserMenu;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.services.UserMenuService;
import com.fu.bmi_tracker.repository.UserMenuRepository;

@Service
public class UserMenuServiceImpl implements UserMenuService {

    @Autowired
    UserMenuRepository repository;

    @Override
    public Iterable<UserMenu> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<UserMenu> findById(Integer id) {
        // return repository.findById(id);
        return null;
    }

    @Override
    public UserMenu save(UserMenu t) {
        return repository.save(t);
    }

    @Override
    public List<UserMenu> saveAll(Iterable<UserMenu> userMenus) {
        return repository.saveAll(userMenus);
    }

    @Override
    public List<UserMenu> findAllByUserID(Integer userID) {
        return repository.findAllByUserUserID(userID);
    }

    @Override
    @Transactional
    public void deleteByUserUserIDAndFoodFoodID(int userID, int foodID) {
        repository.deleteByUserUserIDAndFoodFoodID(userID, foodID);
    }

    @Override
    public void deleteAllByUserUserID(int userID) {
        repository.deleteAllByUserUserID(userID);
    }

}
