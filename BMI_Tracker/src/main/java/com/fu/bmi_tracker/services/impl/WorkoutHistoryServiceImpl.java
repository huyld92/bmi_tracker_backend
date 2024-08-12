/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.Workout;
import com.fu.bmi_tracker.model.entities.WorkoutHistory;
import com.fu.bmi_tracker.repository.MemberRepository;
import com.fu.bmi_tracker.repository.WorkoutHistoryRepository;
import com.fu.bmi_tracker.repository.WorkoutRepository;
import com.fu.bmi_tracker.services.WorkoutHistoryService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkoutHistoryServiceImpl implements WorkoutHistoryService {

    @Autowired
    WorkoutHistoryRepository workoutHistoryRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    WorkoutRepository workoutRepository;

    @Override
    public Iterable<WorkoutHistory> findAll() {
        return workoutHistoryRepository.findAll();
    }

    @Override
    public Optional<WorkoutHistory> findById(Integer id) {
        return workoutHistoryRepository.findById(id);

    }

    @Override
    public WorkoutHistory save(WorkoutHistory t) {
        return workoutHistoryRepository.save(t);
    }

    @Override
    public Iterable<WorkoutHistory> getWorkoutHistoryOfMember(Integer memberID) {
        // gọi workoutHistoryRepository tìm workout history của member
        return workoutHistoryRepository.findByMember_MemberID(memberID);

    }

    public void deactivateActiveWorkoutHistories(int memberID) {
        Iterable<WorkoutHistory> activeWorkoutHistories = workoutHistoryRepository.findByIsActiveTrueAndMember_MemberID(memberID);

        for (WorkoutHistory menuHistory : activeWorkoutHistories) {
            menuHistory.setIsActive(false);
        }
        workoutHistoryRepository.saveAll(activeWorkoutHistories);
    }

    @Override
    public WorkoutHistory assignWorkoutToMember(Integer workoutID, Integer memberID) {

        // gọi member repository tìm member
        Member member = memberRepository.findById(memberID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find member with id{" + memberID + "}!"));
        // gọi workout repository tìm workout 
        Workout workout = workoutRepository.findById(workoutID)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find workout with id{" + workoutID + "}!"));

        if (!workout.getIsActive()) {
            return null;
        }
        // lấy ngày hiện tại
        LocalDate dateOfAssigned = LocalDate.now();

        // tạo WorkoutHistory object
        WorkoutHistory workoutHistory = new WorkoutHistory(
                dateOfAssigned,
                workout,
                member);
        // Deactivate workout đang hoạt động của Member
        deactivateActiveWorkoutHistories(member.getMemberID());

        // lưu workout history mới
        return save(workoutHistory);
    }

    @Override
    public List<String> getMemberNameUsingWorkout(Integer workoutID) {
        return workoutHistoryRepository.findActiveMemberNamesByworkoutID(workoutID);
    }
}
