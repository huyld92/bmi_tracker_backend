/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Exercise;
import com.fu.bmi_tracker.model.entities.Tag;
import com.fu.bmi_tracker.payload.request.CreateExerciseRequest;
import com.fu.bmi_tracker.payload.request.UpdateExerciseRequest;
import com.fu.bmi_tracker.repository.ExerciseRepository;
import com.fu.bmi_tracker.repository.TagRepository;
import com.fu.bmi_tracker.services.ExerciseService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Duc Huy
 */
@Service
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private ExerciseRepository repository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Iterable<Exercise> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Exercise> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Exercise save(Exercise t) {
        return repository.save(t);
    }

    @Override
    public List<Exercise> findByExerciseIDIn(List<Integer> exerciseIDs) {
        return repository.findByExerciseIDIn(exerciseIDs);
    }

    @Override
    public Exercise createExercise(CreateExerciseRequest createExerciseRequest) {
        //Tìm tag từ tagID
        Tag tag = tagRepository.findById(createExerciseRequest.getTagID())
                .orElseThrow(() -> new EntityNotFoundException("Tag id{" + createExerciseRequest.getTagID() + "} not found"));

        // Tạo mới exercise từ createExerciseRequest
        Exercise exercise = new Exercise(createExerciseRequest, tag);

        return repository.save(exercise);
    }

    @Override
    public Exercise updateExercise(UpdateExerciseRequest updateExercerRequest) {
        // tim exercise
        Exercise exercise = repository.findById(updateExercerRequest.getExerciseID())
                .orElseThrow(() -> new EntityNotFoundException("Exercise not found"));

        //Tìm tag từ tagID
        Tag tag = tagRepository.findById(updateExercerRequest.getTagID())
                .orElseThrow(() -> new EntityNotFoundException("Tag id{" + updateExercerRequest.getTagID() + "} not found"));

        // Cập nhật thông tin exercise
        exercise.setExerciseName(updateExercerRequest.getExerciseName());
        exercise.setExercisePhoto(updateExercerRequest.getExercisePhoto());
        exercise.setExerciseVideo(updateExercerRequest.getExerciseVideo());
        exercise.setExerciseDescription(updateExercerRequest.getExerciseDescription());
        exercise.setMet(updateExercerRequest.getMet());
        exercise.setTag(tag);
        
        // cập nhật exercise
        return repository.save(exercise);
    }

    @Override
    @Transactional
    public void deactivateExercise(Integer exerciseID) {
        repository.deactivateById(exerciseID);
    } 

}
