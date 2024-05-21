/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Certificate;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer> {

    public Iterable<Certificate> findAllByAdvisorAdvisorID(int advisorID);

    public boolean existsById(int certidicateID);

    public void deleteById(int certificateID);

    @Modifying
    @Transactional
    @Query("update Certificate c set c.status = ?2 where c.certificateID = ?1")
    public int updateStatusById(Integer certificateID, String status);

}
