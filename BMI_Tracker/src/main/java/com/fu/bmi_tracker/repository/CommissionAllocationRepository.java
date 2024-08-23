/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.CommissionAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface CommissionAllocationRepository extends JpaRepository<CommissionAllocation, Integer> {

    public Iterable<CommissionAllocation> findByCommission_CommissionID(Integer commissionID);

    public Iterable<CommissionAllocation> findBySubscription_SubscriptionNumber(String subscriptionNumber);

}
