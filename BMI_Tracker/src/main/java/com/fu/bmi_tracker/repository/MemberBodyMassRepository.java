/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.MemberBodyMass;
import java.time.LocalDateTime;
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

    @Query("SELECT mbm FROM MemberBodyMass mbm WHERE mbm.member.account.accountID = :accountID")
    public List<MemberBodyMass> findAllByAccountID(Integer accountID);

    @Query("SELECT mbm FROM MemberBodyMass mbm "
            + "JOIN mbm.member m "
            + "WHERE m.account.accountID = :accountID "
            + "ORDER BY mbm.dateInput DESC LIMIT 1")
    public Optional<MemberBodyMass> findLatestByAccountID(Integer accountID);

    @Query("SELECT mbm FROM MemberBodyMass mbm WHERE mbm.member.account.accountID = :accountID"
            + " AND mbm.dateInput >= :startDate AND mbm.dateInput <= :endDate ORDER BY mbm.dateInput DESC")
    List<MemberBodyMass> findRecent30Days(Integer accountID, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT mbm FROM MemberBodyMass mbm WHERE mbm.member.memberID = :memberID"
            + " AND mbm.dateInput >= :startDate AND mbm.dateInput <= :endDate ORDER BY mbm.dateInput DESC")
    List<MemberBodyMass> findRecentIn30DaysByMemberID(Integer memberID, LocalDateTime startDate, LocalDateTime endDate);

}
