/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.DailyRecord;
import com.fu.bmi_tracker.repository.DailyRecordRepository;
import com.fu.bmi_tracker.services.DailyRecordService;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyRecordServiceImpl implements DailyRecordService {

    @Autowired
    DailyRecordRepository repository;

    @Override
    public Iterable<DailyRecord> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<DailyRecord> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public DailyRecord save(DailyRecord t) {
        return repository.save(t);
    }

    @Override
    public Optional<DailyRecord> findByMemberIDAndDate(Integer memberID, LocalDate date) {
        // gọi repository tìm daily records 
        return repository.findByMember_MemberIDAndDate(memberID, date);
    }

    @Override
    public Optional<DailyRecord> findByAccountIDAndDate(Integer accountID, LocalDate date) {

        return repository.findByAccountIDAndDate(accountID, date);
    }

    @Override
    public List<DailyRecord> getDailyRecordsForWeek(Integer memberID, LocalDate date) {
        // Lấy ngày bắt đầu tuần của date
        LocalDate startDate = date.with(DayOfWeek.MONDAY);
        // Lấy ngày kết thúc tuần của date
        LocalDate endDate = date.with(DayOfWeek.SUNDAY);
        return repository.findDailyRecordsForWeek(memberID, startDate, endDate);
    }

}
