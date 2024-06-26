/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.Commission;
import com.fu.bmi_tracker.repository.AdvisorRepository;
import com.fu.bmi_tracker.repository.CommissionRepository;
import com.fu.bmi_tracker.services.CommissionService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommissionServiceImpl implements CommissionService {

    @Autowired
    CommissionRepository commissionRepository;

    @Autowired
    AdvisorRepository advisorRepository;

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

}
