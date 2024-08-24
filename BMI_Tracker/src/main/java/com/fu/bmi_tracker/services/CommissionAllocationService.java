/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.CommissionAllocation;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface CommissionAllocationService extends GeneralService<CommissionAllocation> {

    public Iterable<CommissionAllocation> getAllByCommissionID(Integer commissionID);

    public List<CommissionAllocation> getBySubscriptionNumber(String subscriptionNumber);

}
