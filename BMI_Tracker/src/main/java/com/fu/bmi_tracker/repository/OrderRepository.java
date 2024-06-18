/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Order;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByMemberMemberID(Integer memberID);

    List<Order> findByMember_Account_AccountID(Integer accountID);

    List<Order> findByAdvisor_AdvisorIDAndOrderDateBetween(Integer advisorID, LocalDateTime startOfMonth, LocalDateTime endOfMonth);

    List<Order> findByAdvisor_AdvisorIDOrderByOrderDateDesc(Integer advisorID);

}
