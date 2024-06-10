/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Order;
import com.fu.bmi_tracker.repository.OrderRepository;
import com.fu.bmi_tracker.services.OrderService;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository repository;

    @Override
    public Iterable<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Order save(Order t) {
        return repository.save(t);
    }

    @Override
    public Iterable<Order> getOrderByMemberAccountID(Integer accountID) {
        return repository.findByMember_AccountID(accountID);
    }

    @Override
    public Iterable<Order> getOrderByMemberID(Integer memberID) {
        return repository.findByMemberMemberID(memberID);

    }

    @Override
    public Iterable<Order> getOrderByAdvisorIDAndMonth(Integer advisorID, String month) {
        // Chuyển đổi chuỗi "yyyy-MM" thành YearMonth
        YearMonth yearMonthObj = YearMonth.parse(month);
        
        // Tạo LocalDateTime cho ngày đầu tiên của tháng
        LocalDateTime startOfMonth = yearMonthObj.atDay(1).atStartOfDay();
        
        // Tạo LocalDateTime cho ngày cuối cùng của tháng
        LocalDateTime endOfMonth = yearMonthObj.atEndOfMonth().atTime(23, 59, 59);
        
        // Gọi phương thức find order bằng advisor ID và Order date nằm trong khoảng start-end từ repository
        return repository.findByAdvisor_AdvisorIDAndDateOrderBetween(advisorID, startOfMonth, endOfMonth);
     }

}
