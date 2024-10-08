/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.Certificate;
import com.fu.bmi_tracker.payload.request.CreateAdvisorCertificateRequest;
import com.fu.bmi_tracker.payload.request.CreateCertificateRequest;
import com.fu.bmi_tracker.repository.AdvisorRepository;
import com.fu.bmi_tracker.repository.CertificateRepository;
import com.fu.bmi_tracker.services.CertificateService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificateServiceImpl implements CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private AdvisorRepository advisorRepository;

    @Override
    public Iterable<Certificate> findAll() {
        return certificateRepository.findAll();
    }

    @Override
    public Optional<Certificate> findById(Integer id) {
        return certificateRepository.findById(id);
    }

    @Override
    public Certificate save(Certificate t) {
        return certificateRepository.save(t);
    }

    @Override
    public Iterable<Certificate> findAllByAdvisorAdvisorID(int advisorID) {
        return certificateRepository.findAllByAdvisorAdvisorID(advisorID);
    }

    @Override
    public boolean existsById(int certificateID) {
        return certificateRepository.existsById(certificateID);
    }

    @Override
    public void deleteById(int certificateID) {
        certificateRepository.deleteById(certificateID);
    }

    @Override
    public Iterable<Certificate> findAllOfAdvisor(Integer accountID) {
        // Tim advisor từ accountID
        Advisor advisor = advisorRepository.findByAccount_AccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find advisor!"));
        // gọi repository tìm tất cả certificate của Advisor
        return certificateRepository.findAllByAdvisorAdvisorID(advisor.getAdvisorID());
    }

    @Override
    public Certificate createNewCertificate(CreateCertificateRequest certificateRequest) {
        // Tim advisor từ advisorID
        Advisor advisor = advisorRepository.findById(certificateRequest.getAdvisorID())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find advisor!"));

        //tạo object certificate
        Certificate c = new Certificate(certificateRequest, advisor);

        return save(c);
    }

    @Override
    public Certificate createNewCertificate(CreateAdvisorCertificateRequest certificateRequest, int accountID) {
        // Tim advisor từ advisorID
        Advisor advisor = advisorRepository.findByAccount_AccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find advisor!"));

        //tạo object certificate
        Certificate c = new Certificate(
                certificateRequest.getCertificateName(),
                certificateRequest.getCertificateLink(),
                advisor);

        return save(c);
    }

}
