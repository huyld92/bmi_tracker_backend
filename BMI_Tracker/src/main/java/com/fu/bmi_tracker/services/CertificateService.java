/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Certificate;
import java.util.List;

/**
 *
 * @author Duc Huy
 */
public interface CertificateService extends GeneralService<Certificate> {

    public Iterable<Certificate> findAllByAdvisorAdvisorID(int advisorID);

    public boolean existsById(int certificateID);

    public void deleteById(int certificateID);

    public Iterable<Certificate> findAllOfAdvisor(Integer accountID);

}
