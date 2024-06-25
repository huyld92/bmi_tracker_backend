/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Tag;
import com.fu.bmi_tracker.model.entities.Workout;
import com.fu.bmi_tracker.payload.request.UpdateWorkoutRequest;
import com.fu.bmi_tracker.repository.TagRepository;
import com.fu.bmi_tracker.repository.WorkoutRepository;
import com.fu.bmi_tracker.services.WorkoutService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    TagRepository tagRepository;

    @Override
    public Iterable<Workout> findAll() {
        return workoutRepository.findAll();
    }

    @Override
    public Optional<Workout> findById(Integer id) {
        return workoutRepository.findById(id);
    }

    @Override
    public Workout save(Workout t) {
        return workoutRepository.save(t);
    }

    @Override
    public Workout updateWorkoutInformation(UpdateWorkoutRequest updateWorkoutRequest) {
        // tìm workout bằng workout id
        Workout workout = workoutRepository.findById(updateWorkoutRequest.getWorkoutID())
                .orElseThrow(() -> new EntityNotFoundException("Workout not found"));
        // cập nhật thông tin workout
        workout.setWorkoutName(updateWorkoutRequest.getWorkoutName());
        workout.setWorkoutDescription(updateWorkoutRequest.getWorkoutDescription());
        workout.setTotalCloriesBurned(updateWorkoutRequest.getTotalCaloriesBurned());
        workout.setIsActive(updateWorkoutRequest.getIsActive());
        // lưu lại thông tin mới và trả Workout
        return workoutRepository.save(workout);
    }

    @Override
    public Iterable<Workout> getWorkoutByAdvisorID(Integer advisorID) {
        return workoutRepository.findByAdvisorID(advisorID);
    }

    @Override
    public Workout createNewWorkout(Workout workout, List<Integer> tagIDs) {
        // tìm list tag từ list tagID
        List<Tag> tags = tagRepository.findByTagIDIn(tagIDs);
        // set tags cho menu
        workout.setTags(tags);

        // lưu trữ Menu
        return save(workout);
    }

}
