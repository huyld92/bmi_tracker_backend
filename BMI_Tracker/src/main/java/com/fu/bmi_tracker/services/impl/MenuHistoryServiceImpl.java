/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.Menu;
import com.fu.bmi_tracker.model.entities.MenuHistory;
import com.fu.bmi_tracker.repository.MemberRepository;
import com.fu.bmi_tracker.repository.MenuHistoryRepository;
import com.fu.bmi_tracker.repository.MenuRepository;
import com.fu.bmi_tracker.services.MenuHistoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuHistoryServiceImpl implements MenuHistoryService {

    @Autowired
    MenuHistoryRepository menuHistoryRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    MemberRepository memberRepository;

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

    @Transactional
    public void deactivateActiveMenuHistories(Integer memberID) {
        Iterable<MenuHistory> activeMenuHistories = menuHistoryRepository.findByIsActiveTrueAndMember_MemberID(memberID);

        for (MenuHistory menuHistory : activeMenuHistories) {
            menuHistory.setIsActive(false);
        }
        menuHistoryRepository.saveAll(activeMenuHistories);
    }

    @Override
    public MenuHistory assignMenuToMember(Integer menuID, Integer memberID) {
        // gọi member repository tìm member
        Member member = memberRepository.findById(memberID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find member with id{" + memberID + "}!"));

        // gọi menu repository tìm menu 
        Menu menu = menuRepository.findById(menuID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find menu with id{" + menuID + "}!"));

        if (!menu.getIsActive()) {
            return null;
        }

        // lấy ngày hiện tại
        LocalDate dateOfAssigned = LocalDate.now();

        // tạo MenuHistory object
        MenuHistory menuHistory = new MenuHistory(
                dateOfAssigned,
                menu,
                member);

        // Deactivate menu đang hoạt động của Member
        deactivateActiveMenuHistories(member.getMemberID());

        // lưu menu history mới
        return save(menuHistory);
    }

    @Override
    public List<String> getMemberNameUsingMenu(Integer menuID) {
        return menuHistoryRepository.findActiveMemberNamesByMenuID(menuID);
    }

}
