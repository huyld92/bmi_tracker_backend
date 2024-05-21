/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.MemberMenu;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.services.MemberMenuService;
import com.fu.bmi_tracker.repository.MemberMenuRepository;

@Service
public class MemberMenuServiceImpl implements MemberMenuService {

    @Autowired
    MemberMenuRepository repository;

    @Override
    public Iterable<MemberMenu> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<MemberMenu> findById(Integer id) {
        // return repository.findById(id);
        return null;
    }

    @Override
    public MemberMenu save(MemberMenu t) {
        return repository.save(t);
    }

    @Override
    public List<MemberMenu> saveAll(Iterable<MemberMenu> memberMenus) {
        return repository.saveAll(memberMenus);
    }

    @Override
    public List<MemberMenu> findAllByMemberID(Integer memberID) {
        return repository.findAllByMemberMemberID(memberID);
    }

    @Override
    @Transactional
    public void deleteByMemberMemberIDAndFoodFoodID(int memberID, int foodID) {
        repository.deleteByMemberMemberIDAndFoodFoodID(memberID, foodID);
    }

    @Override
    public void deleteAllByMemberMemberID(int memberID) {
        repository.deleteAllByMemberMemberID(memberID);
    }

}
