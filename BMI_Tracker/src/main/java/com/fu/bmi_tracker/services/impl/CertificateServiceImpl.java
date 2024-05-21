/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Certificate;
import com.fu.bmi_tracker.repository.CertificateRepository;
import com.fu.bmi_tracker.services.CertificateService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificateServiceImpl implements CertificateService {

    @Autowired
    CertificateRepository repository;

    @Override
    public Iterable<Certificate> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Certificate> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Certificate save(Certificate t) {
        return repository.save(t);
    }

    @Override
    public Iterable<Certificate> findAllByAdvisorAdvisorID(int advisorID) {
        return repository.findAllByAdvisorAdvisorID(advisorID);
    }

    @Override
    public boolean existsById(int certificateID) {
        return repository.existsById(certificateID);
    }

    @Override
    public void deleteById(int certificateID) {
        repository.deleteById(certificateID);
    }

    @Override
    public int updateStatusById(Integer certificateID, String status) {
        return repository.updateStatusById(certificateID, status);
    }

}
