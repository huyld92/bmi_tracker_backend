/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Package;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author BaoLG
 */
public interface PackageRepository extends JpaRepository<Package, Integer> {

    public Iterable<Package> findByAdvisor_AdvisorID(int advisorID);

    public Iterable<Package> findByAdvisor_AdvisorIDAndIsActiveTrue(int advisorID);

    public Iterable<Package> findByAdvisor_AdvisorIDAndIsActiveTrueAndPackageStatus(Integer advisorID, String packageStatus);

    public Iterable<Package> findByIsActiveTrue();
}
