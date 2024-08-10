/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Package;

import com.fu.bmi_tracker.payload.request.CreatePackageRequest;

/**
 *
 * @author BaoLG
 */
public interface PackageService extends GeneralService<Package> {

    //Find All Package by AdvisorID
    public Iterable<Package> findAllPackageByAdvisorID(int advisorID);

    //Find All Package by isActive = true;
    public Iterable<Package> findAllAvailablePackage();

    public Package createPackage(CreatePackageRequest newPackage, Integer accountID);

    public Iterable<Package> findAllPackageFromPersonally(Integer accountID);

    public Iterable<Package> getAllPackageForSubscription(Integer advisorID);
}
