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
    DailyRecordRepository dailyRecordRepository;

    @Override
    public Iterable<DailyRecord> findAll() {
        return dailyRecordRepository.findAll();
    }

    @Override
    public Optional<DailyRecord> findById(Integer id) {
        return dailyRecordRepository.findById(id);
    }

    @Override
    public DailyRecord save(DailyRecord t) {
        return dailyRecordRepository.save(t);
    }

    @Override
    public Optional<DailyRecord> findByMemberIDAndDate(Integer memberID, LocalDate date) {
        // gọi repository tìm daily records 
        return dailyRecordRepository.findByMember_MemberIDAndDate(memberID, date);
    }

    @Override
    public Optional<DailyRecord> findByAccountIDAndDate(Integer accountID, LocalDate date) {

        return dailyRecordRepository.findByAccountIDAndDate(accountID, date);
    }

    @Override
    public List<DailyRecord> getDailyRecordsForWeek(Integer memberID, LocalDate date) {
        // Lấy ngày bắt đầu tuần của date
        LocalDate startDate = date.minusDays(7);
        // Lấy ngày kết thúc tuần của date
//        LocalDate endDate = date.with(DayOfWeek.SUNDAY);
        return dailyRecordRepository.findByMemberIDWithStarEndDate(memberID, startDate, date);
    }

    //chuyển đổi từ DailyRecord sáng FullResponse
//    private DailyRecordFullResponse toDailyRecordFullResponse(DailyRecord dailyRecord) {
//        List<ActivityLogResponse> activityLogs = dailyRecord.getActivityLogs().stream()
//                .map(activityLog -> new ActivityLogResponse(activityLog))
//                .collect(Collectors.toList());
//
//        List<MealLogResponse> mealLogs = dailyRecord.getMealLogs().stream()
//                .map(mealLog -> new MealLogResponse(
//                mealLog.getMealLogID(),
//                mealLog.getFoodName(),
//                mealLog.getCalories(),
//                mealLog.getMealType(),
//                mealLog.getFoodID()
//        ))
//                .collect(Collectors.toList());
//
//        return new DailyRecordFullResponse(
//                dailyRecord.getRecordID(),
//                dailyRecord.getTotalCaloriesIn(),
//                dailyRecord.getTotalCaloriesOut(),
//                dailyRecord.getDefaultCalories(),
//                dailyRecord.getDate(),
//                activityLogs,
//                mealLogs);
//    }
}
