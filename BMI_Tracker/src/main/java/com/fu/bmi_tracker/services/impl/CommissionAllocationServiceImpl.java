/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.CommissionAllocation;
import com.fu.bmi_tracker.repository.CommissionAllocationRepository;
import com.fu.bmi_tracker.services.CommissionAllocationService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommissionAllocationServiceImpl implements CommissionAllocationService {

    @Autowired
    private CommissionAllocationRepository allocationRepository;

    @Override
    public Iterable<CommissionAllocation> getAllByCommissionID(Integer commissionID) {
        return allocationRepository.findByCommission_CommissionID(commissionID);
    }

    @Override
    public Iterable<CommissionAllocation> findAll() {
        return allocationRepository.findAll();
    }

    @Override
    public Optional<CommissionAllocation> findById(Integer id) {
        return allocationRepository.findById(id);
    }

    @Override
    public CommissionAllocation save(CommissionAllocation t) {
        return allocationRepository.save(t);
    }

    @Override
    public List<CommissionAllocation> getBySubscriptionNumber(String subscriptionNumber) {
        return allocationRepository.findBySubscription_SubscriptionNumber(subscriptionNumber);

    }

}
