/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.MenuHistory;
import com.fu.bmi_tracker.repository.MemberRepository;
import com.fu.bmi_tracker.repository.MenuHistoryRepository;
import com.fu.bmi_tracker.services.MenuHistoryService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuHistoryServiceImpl implements MenuHistoryService {

    @Autowired
    MenuHistoryRepository menuHistoryRepository; 

    @Override
    public Iterable<MenuHistory> getMenuHistoryOfMember(Integer memberID) {

        // gọi menuHistoryRepository tìm menu history của member
        return menuHistoryRepository.findByMember_MemberID(memberID);

    }

    @Override
    public Iterable<MenuHistory> findAll() {
        return menuHistoryRepository.findAll();
    }

    @Override
    public Optional<MenuHistory> findById(Integer id) {
        return menuHistoryRepository.findById(id);
    }

    @Override
    public MenuHistory save(MenuHistory t) {
        return menuHistoryRepository.save(t);
    }

}
