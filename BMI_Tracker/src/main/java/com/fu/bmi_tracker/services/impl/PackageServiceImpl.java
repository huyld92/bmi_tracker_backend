/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.Package;
import com.fu.bmi_tracker.model.enums.EStatus;
import com.fu.bmi_tracker.payload.request.CreatePackageRequest;
import com.fu.bmi_tracker.repository.AdvisorRepository;
import com.fu.bmi_tracker.repository.PackageRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.services.PackageService;

/**
 *
 * @author BaoLG
 */
@Service
public class PackageServiceImpl implements PackageService {

    @Autowired
    PackageRepository packageRepository;

    @Autowired
    AdvisorRepository advisorRepository;

    @Override
    public List<Package> findAll() {
        return packageRepository.findAll();
    }

    @Override
    public Iterable<Package> findAllPackageByAdvisorID(int advisorID) {
        return packageRepository.findByAdvisor_AdvisorID(advisorID);
    }

    @Override
    public Iterable<Package> findAllAvailablePackage() {
        return packageRepository.findByIsActiveTrue();
    }

    @Override
    public Optional<Package> findById(Integer id) {
        return packageRepository.findById(id);
    }

    @Override
    public Package save(Package t) {
        return packageRepository.save(t);
    }

    @Override
    public Package createPackage(CreatePackageRequest packageRequest, Integer accountID) {
        //Convert createRequest to Package before save
        Package p = new Package();
        p.setPackageName(packageRequest.getPackageName());
        p.setPrice(packageRequest.getPrice());
        p.setDescription(packageRequest.getDescription());
        p.setPackageDuration(packageRequest.getPackageDuration());
        p.setNumberOfUses(0);
        p.setPackageStatus(EStatus.PENDING.toString());
        p.setPackageCode("PLAN-");
        // mặc định false đợi manager duyệt
        p.setPackageStatus(EStatus.PENDING.toString());
        p.setIsActive(Boolean.TRUE);

        // find Advisor
        Advisor advisor = advisorRepository.findByAccount_AccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find advisor!"));

        // set advisor ID
        p.setAdvisor(advisor);
        Package packageSaved = save(p);
        packageSaved.setPackageCode("PA-" + packageSaved.getPackageID());

        return save(packageSaved);
    }

    @Override
    public Iterable<Package> findAllPackageFromPersonally(Integer accountID) {
        // find Advisor
        Advisor advisor = advisorRepository.findByAccount_AccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find advisor!"));

        // GỌi repository tìm tất cả package của advisor
        return packageRepository.findByAdvisor_AdvisorIDAndIsActiveTrue(advisor.getAdvisorID());
    }

    @Override
    public Iterable<Package> getAllPackageForSubscription(Integer advisorID) {
        return packageRepository.findByAdvisor_AdvisorIDAndIsActiveTrueAndPackageStatus(advisorID, EStatus.APPROVED.toString());
    }

}
