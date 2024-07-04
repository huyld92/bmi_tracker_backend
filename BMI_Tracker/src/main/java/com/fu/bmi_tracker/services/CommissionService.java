/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Commission;
import com.fu.bmi_tracker.payload.request.UpdateCommissionRequest;

/**
 *
 * @author Duc Huy
 */
public interface CommissionService extends GeneralService<Commission> {

    public Iterable<Commission> getByAdvisor(Integer accountID);

    public Iterable<Commission> getByAdvisorID(Integer advisorID);

    public Commission updateCommission(UpdateCommissionRequest commissionRequest);

}
