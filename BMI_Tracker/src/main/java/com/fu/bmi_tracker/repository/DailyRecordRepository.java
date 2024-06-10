/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.DailyRecord;
import java.time.LocalDate;
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
public interface DailyRecordRepository extends JpaRepository<DailyRecord, Integer> {

    public Optional<DailyRecord> findByMember_MemberIDAndDate(Integer memberID, LocalDate date);

    @Query("SELECT dr FROM DailyRecord dr WHERE dr.member.accountID = :accountID AND dr.date = :date")
    Optional<DailyRecord> findByAccountIDAndDate(Integer accountID, LocalDate date);

    // Tìm list daily record nằm trong khoảng startDate và Endate
    @Query("SELECT dr FROM DailyRecord dr WHERE dr.member.id = :memberID AND dr.date BETWEEN :startDate AND :endDate")
    List<DailyRecord> findDailyRecordsForWeek( Integer memberID,
             LocalDate startDate,
           LocalDate endDate);

}
