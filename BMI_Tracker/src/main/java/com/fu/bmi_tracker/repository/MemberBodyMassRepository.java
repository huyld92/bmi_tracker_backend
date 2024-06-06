/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.MemberBodyMass;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface MemberBodyMassRepository extends JpaRepository<MemberBodyMass, Integer> {

    Optional<MemberBodyMass> findFirstByMemberMemberIDOrderByDateInputDesc(Integer memberId);

    @Query("SELECT mbm FROM MemberBodyMass mbm WHERE mbm.member.accountID = :accountID")
    public List<MemberBodyMass> findAllByAccountID(Integer accountID);
}
