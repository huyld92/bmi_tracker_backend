/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.MealLog;
import com.fu.bmi_tracker.model.enums.EMealType;
import com.fu.bmi_tracker.repository.MealLogRepository;
import com.fu.bmi_tracker.services.MealLogService;
import java.time.LocalDate;
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
    public Iterable<MealLog> findAllByDateOfMeal(LocalDate dateOfMeal) {
        return repository.findAllByDateOfMeal(dateOfMeal);
    }

    @Override
    public Iterable<MealLog> findAllByDateOfMealAndMember_MemberID(LocalDate dateOfMeal, int memberID) {
        return repository.findAllByDateOfMealAndMember_MemberID(dateOfMeal, memberID);
    }

    @Override
    public Iterable<Integer> findFoodIDByDateOfMealAndMemberIDAndMealType(LocalDate dateOfMeal, int memberID, EMealType mealType) {
        return repository.findFoodIDByDateOfMealAndMember_MemberIDAndMealType(dateOfMeal, memberID, mealType);
    }

}
