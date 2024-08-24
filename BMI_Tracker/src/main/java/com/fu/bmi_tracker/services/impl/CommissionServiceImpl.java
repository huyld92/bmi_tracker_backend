/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.Commission;
import com.fu.bmi_tracker.payload.request.UpdateCommissionRequest;
import com.fu.bmi_tracker.payload.response.CommissionSummaryResponse;
import com.fu.bmi_tracker.repository.AdvisorRepository;
import com.fu.bmi_tracker.repository.CommissionRepository;
import com.fu.bmi_tracker.services.CommissionService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommissionServiceImpl implements CommissionService {

    @Autowired
    private CommissionRepository commissionRepository;

    @Autowired
    private AdvisorRepository advisorRepository;

    @Override
    public Iterable<Commission> findAll() {
        return commissionRepository.findAll();
    }

    @Override
    public Optional<Commission> findById(Integer id) {
        return commissionRepository.findById(id);
    }

    @Override
    public Commission save(Commission t) {
        return commissionRepository.save(t);
    }

    @Override
    public Iterable<Commission> getByAdvisor(Integer accountID) {
        // tìm advisor bằng accountID
        Advisor advisor = advisorRepository.findByAccount_AccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find advisor!"));

        // gọi commissionRepository tìm tất cả commission của advisor
        return commissionRepository.findByAdvisor_AdvisorID(advisor.getAdvisorID());
    }

    @Override
    public Iterable<Commission> getByAdvisorID(Integer advisorID) {
        // gọi commissionRepository tìm tất cả commission của advisor
        return commissionRepository.findByAdvisor_AdvisorID(advisorID);
    }

    @Override
    public Commission updateCommission(UpdateCommissionRequest commissionRequest) {
        // tìm commission bằng commission ID
        Commission commission = commissionRepository.findById(commissionRequest.getCommissionID())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find commission with id{" + commissionRequest.getCommissionID() + "}!"));

        commission.update(commissionRequest);
        return save(commission);
    }

    @Override
    public List<CommissionSummaryResponse> getCommissionSummaryIn6Months() {
        // lấy tất cả các commission trước ngày hiện tại trong vòng 6 tháng
        LocalDate startDate = LocalDate.now().minusMonths(5).withDayOfMonth(1);
        LocalDate endDate = LocalDate.now();
        return commissionRepository.countCommissionsPerMonth(startDate, endDate);
    }

}
