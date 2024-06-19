/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.MemberTransaction;
import com.fu.bmi_tracker.model.entities.Order;
import com.fu.bmi_tracker.payload.request.CreateOrderTransactionRequest;
import com.fu.bmi_tracker.repository.AdvisorRepository;
import com.fu.bmi_tracker.repository.MemberRepository;
import com.fu.bmi_tracker.repository.MemberTransactionRepository;
import com.fu.bmi_tracker.repository.OrderRepository;
import com.fu.bmi_tracker.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AdvisorRepository advisorRepository;

    @Autowired
    MemberTransactionRepository transactionRepository;

    @Override
    public Iterable<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order save(Order t) {
        return orderRepository.save(t);
    }

    @Override
    public Iterable<Order> getOrderByMemberAccountID(Integer accountID) {
        return orderRepository.findByMember_Account_AccountID(accountID);
    }

    @Override
    public Iterable<Order> getOrderByMemberID(Integer memberID) {
        return orderRepository.findByMemberMemberID(memberID);

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
        return orderRepository.findByAdvisor_AdvisorIDAndOrderDateBetween(advisorID, startOfMonth, endOfMonth);
    }

    @Override
    public Order createOrderTransaction(CreateOrderTransactionRequest createRequest, Integer accountID) {
        // Tìm member băng accountID
        Member member = memberRepository.findByAccount_AccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find member!"));

        // Tạo member transaction
        MemberTransaction memberTrasaction = new MemberTransaction(
                createRequest.getTransactionRequest(),
                member.getMemberID());

        // nhận transaction sao khi lưu xuống database
        MemberTransaction transaction = transactionRepository.save(memberTrasaction);

        // tạo order
        Order order = new Order(createRequest.getOrderRequest(),
                member.getMemberID(),
                transaction.getTransactionID());
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrderByMemberAdvisor(Integer accountID) {
        // Tim advisor từ accountID
        Advisor advisor = advisorRepository.findByAccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find advisor!"));

        // Tìm order từ account ID với sắp xếp mới nhất
        return orderRepository.findByAdvisor_AdvisorIDOrderByOrderDateDesc(advisor.getAdvisorID());
    }

    @Override
    public List<Member> getCurrentMemeberOfAdvisor(Integer accountID) {
        // lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();

        // gợi Order repository tìm danh sách Order bằng accountID và endDate > currentDate
        List<Order> orders = orderRepository.findByAdvisor_AccountIDAndEndDateGreaterThan(accountID, currentDate);

        // chuyển đổi từ order sang List<Member> bằng stream(), distinct đảm bảo không trùng Member
        return orders.stream().map(Order::getMember).distinct()
                .collect(Collectors.toList());
    }

}
