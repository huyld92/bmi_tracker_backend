/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.ActivityLog;
import com.fu.bmi_tracker.model.entities.DailyRecord;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.payload.request.CreateActivityLogRequest;
import com.fu.bmi_tracker.payload.request.UpdateActivityLogRequest;
import com.fu.bmi_tracker.repository.ActivityLogRepository;
import com.fu.bmi_tracker.repository.DailyRecordRepository;
import com.fu.bmi_tracker.repository.MemberRepository;
import com.fu.bmi_tracker.services.ActivityLogService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {

    @Autowired
    private ActivityLogRepository activityRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    DailyRecordRepository dailyRecordRepository;

    @Override
    public Iterable<ActivityLog> findAll() {
        return activityRepository.findAll();
    }

    @Override
    public Optional<ActivityLog> findById(Integer id) {
        return activityRepository.findById(id);
    }

    @Override
    public ActivityLog save(ActivityLog t) {
        return activityRepository.save(t);
    }

    @Override
    public Iterable<ActivityLog> findByRecordID(Integer recordID) {
        return activityRepository.findByRecordID(recordID);
    }

    @Override
    public void deleteById(int activityLogID) {
        activityRepository.deleteById(activityLogID);
    }

    @Override
    public ActivityLog createActivityLog(CreateActivityLogRequest activityLogRequest, LocalDate dateOfActivity, Integer accountID) {
        // kiểm tra tồn tại daily record từ accountID và dateOfActivity
        DailyRecord dailyRecord = existedDailyRecord(accountID, activityLogRequest.getCaloriesBurned(), dateOfActivity);
        if (activityLogRequest.getExerciseID() < 0) {
            // ngược lại chưa tồn tại => tạo mới activity log 
            ActivityLog activityLog = new ActivityLog(activityLogRequest, dailyRecord.getRecordID());

            //gọi repository lưu activity log
            return activityRepository.save(activityLog);
        }

        // kiểm tra activity log với exerciseID đã tồn tại chưa
        Optional<ActivityLog> optionalActivityLog = dailyRecord.getActivityLogs().stream()
                .filter(aL -> activityLogRequest.getExerciseID().equals(aL.getExerciseID()))
                .findFirst();

        // nếu tồn tại activity log
        if (optionalActivityLog.isPresent()) {
            // cập nhật lại giá trị activiLog
            // tính caloriesBurned
            Integer caloriesBurned = optionalActivityLog.get().getCaloriesBurned() + activityLogRequest.getCaloriesBurned();

            // tính lại duration và cập nhật 
            Integer duration = optionalActivityLog.get().getDuration() + activityLogRequest.getDuration();
            optionalActivityLog.get().setCaloriesBurned(caloriesBurned);
            optionalActivityLog.get().setDuration(duration);

            // gọi activity repository cập nhật lại activity log
            return save(optionalActivityLog.get());
        } else {
            // ngược lại chưa tồn tại => tạo mới activity log 
            ActivityLog activityLog = new ActivityLog(activityLogRequest, dailyRecord.getRecordID());

            //gọi repository lưu activity log
            return activityRepository.save(activityLog);
        }
    }

    @Override
    public ActivityLog updateActivityLog(UpdateActivityLogRequest activityLogRequest) {
        // tìm activityLog bằng ID
        ActivityLog activityLog = activityRepository
                .findById(activityLogRequest.getActivityID())
                .orElseThrow(() -> new EntityNotFoundException("Cannot find activity log id{" + activityLogRequest.getActivityID() + "}!"));

        // tìm Daily record
        DailyRecord dailyRecord = dailyRecordRepository.findById(activityLog.getRecordID()).get();

        // tính lại caloriesOut
        int caloriesOut = dailyRecord.getTotalCaloriesOut() - activityLog.getCaloriesBurned() + activityLogRequest.getCaloriesBurned();
        dailyRecord.setTotalCaloriesOut(caloriesOut);

        // gọi dailyRecordRepository cập nhật dailyRecord
        dailyRecordRepository.save(dailyRecord);

        // cập nhật lại Activity log 
        activityLog.setActivityName(activityLogRequest.getActivityName());
        activityLog.setCaloriesBurned(activityLogRequest.getCaloriesBurned());
        activityLog.setDuration(activityLogRequest.getDuration());

        // trả về kết quả cập nhật
        return save(activityLog);
    }

    private DailyRecord existedDailyRecord(Integer accountID, Integer caloriesBurned, LocalDate dateOfActivity) {
        // tìm member băng account ID
        Member member = memberRepository.findByAccount_AccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find member!"));

        // tìm daily record theo date 
        Optional<DailyRecord> dailyRecord = dailyRecordRepository.findByMember_MemberIDAndDate(
                member.getMemberID(), dateOfActivity);

        // nếu chưa tồn tại record thì create new
        if (!dailyRecord.isPresent()) {
            // activityLog 
            dailyRecord = Optional.of(
                    dailyRecordRepository.save(
                            new DailyRecord(
                                    dateOfActivity,
                                    0,
                                    caloriesBurned,
                                    member.getDefaultCalories(),
                                    member
                            )));
        } else {
            // ngược lại cập nhật thông tin calories out
            int caloriesOut = dailyRecord.get().getTotalCaloriesOut() + caloriesBurned;
            dailyRecord.get().setTotalCaloriesOut(caloriesOut);

            // lưu thông tin dailyrecord đã cập nhật
            dailyRecordRepository.save(dailyRecord.get());
        }

        return dailyRecord.get();
    }

}
