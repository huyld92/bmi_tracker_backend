/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.MealLog;
import com.fu.bmi_tracker.model.enums.EMealType;
import com.fu.bmi_tracker.payload.request.UpdateMealLogRequest;
import com.fu.bmi_tracker.repository.MealLogRepository;
import com.fu.bmi_tracker.services.MealLogService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MealLogServiceImpl implements MealLogService {

    @Autowired
    MealLogRepository repository;

    @Override
    public Iterable<MealLog> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<MealLog> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public MealLog save(MealLog t) {
        return repository.save(t);
    }

    @Override
    public void deleteById(int mealLogID) {
        repository.deleteById(mealLogID);
    }

    @Override
    public Iterable<MealLog> findByRecordID(Integer recordID) {
        return repository.findByRecordID(recordID);
    }

    @Override
    public Iterable<MealLog> findByRecordIDAndByMealType(Integer recordID, EMealType mealType) {
        return repository.findByRecordIDAndMealType(recordID, mealType);
    }

    @Override
    public MealLog updateMealLog(UpdateMealLogRequest mealLogRequest) {
        // tìm meal log bằng meal log ID
        MealLog mealLog = repository
                .findById(mealLogRequest.getMealLogID())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find meal log!"));

        // cập nhật lại meal log
        mealLog.setCalories(mealLogRequest.getCalories());
        mealLog.setQuantity(mealLogRequest.getQuantity());
        return repository.save(mealLog);
    }

}
