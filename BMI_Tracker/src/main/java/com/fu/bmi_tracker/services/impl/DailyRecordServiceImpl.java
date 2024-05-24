/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.DailyRecord;
import com.fu.bmi_tracker.repository.DailyRecordRepository;
import com.fu.bmi_tracker.services.DailyRecordService;
import java.time.LocalDate;
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
        return repository.findByMemberIDAndDate(memberID, date);
    }

}
