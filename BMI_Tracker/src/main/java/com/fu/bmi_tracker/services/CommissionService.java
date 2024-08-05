/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Commission;
import com.fu.bmi_tracker.payload.request.UpdateCommissionRequest;
import com.fu.bmi_tracker.payload.response.CommissionSummaryResponse;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface CommissionService extends GeneralService<Commission> {

    public Iterable<Commission> getByAdvisor(Integer accountID);

    public Iterable<Commission> getByAdvisorID(Integer advisorID);

    public Commission updateCommission(UpdateCommissionRequest commissionRequest);

    public List<CommissionSummaryResponse> getCommissionSummaryIn6Months(); 

}
