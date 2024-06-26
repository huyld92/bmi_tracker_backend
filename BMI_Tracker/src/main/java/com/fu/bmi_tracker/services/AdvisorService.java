/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.payload.response.AdvisorResponse;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface AdvisorService extends GeneralService<Advisor> {

    public Advisor findByAccountID(Integer accountID);

    public List<Advisor> findAllAdvisorsWithDetails();

}
