/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.WorkoutHistory;
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
public interface WorkoutHistoryRepository extends JpaRepository<WorkoutHistory, Integer> {

    public Optional<WorkoutHistory> findByMember_MemberIDAndIsActiveTrue(Integer memberID);

    public Iterable<WorkoutHistory> findByMember_MemberID(Integer memberID);

    public Iterable<WorkoutHistory> findByIsActiveTrue();

    public Iterable<WorkoutHistory> findByIsActiveTrueAndMember_MemberID(int memberID);

    @Query("SELECT wh.member.account.fullName FROM WorkoutHistory wh WHERE wh.workout.workoutID = :workoutID AND wh.isActive = true")
    public List<String> findActiveMemberNamesByworkoutID(Integer workoutID);

}
