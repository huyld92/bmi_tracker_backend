/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.MenuHistory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface MenuHistoryRepository extends JpaRepository<MenuHistory, Integer> {

    public Optional<MenuHistory> findByMember_MemberIDAndIsActiveTrue(Integer memberID);

    public Iterable<MenuHistory> findByMember_MemberID(Integer memberID);

    public Iterable<MenuHistory> findByIsActiveTrue();

}
