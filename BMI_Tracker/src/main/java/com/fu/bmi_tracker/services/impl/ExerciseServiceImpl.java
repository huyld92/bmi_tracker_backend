/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Exercise;
import com.fu.bmi_tracker.model.entities.TagExercise;
import com.fu.bmi_tracker.model.entities.Tag;
import com.fu.bmi_tracker.payload.request.CreateExerciseRequest;
import com.fu.bmi_tracker.payload.request.UpdateExercerRequest;
import com.fu.bmi_tracker.repository.ExerciseRepository;
import com.fu.bmi_tracker.repository.TagRepository;
import com.fu.bmi_tracker.services.ExerciseService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.repository.TagExerciseRepository;

/**
 *
 * @author Duc Huy
 */
@Service
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    ExerciseRepository repository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    TagExerciseRepository exerciseTagRepository;

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
        //Tìm danh sách tags từ List tagIDs
        List<Tag> tags = tagRepository.findByTagIDIn(createExerciseRequest.getTagIDs());

        // Tạo mới exercise từ createExerciseRequest
        Exercise exercise = new Exercise(createExerciseRequest, tags);

        return repository.save(exercise);
    }

    @Override
    public Exercise updateExercise(UpdateExercerRequest updateExercerRequest) {
        // tim exercise
        Exercise exercise = repository.findById(updateExercerRequest.getExerciseID())
                .orElseThrow(() -> new EntityNotFoundException("Exercise not found"));

        // Cập nhật thông tin exercise
        exercise.setExerciseName(updateExercerRequest.getExerciseName());
        exercise.setCaloriesBurned(updateExercerRequest.getCaloriesBurned());
        exercise.setDuration(updateExercerRequest.getDuration());
        exercise.setDistance(updateExercerRequest.getDistance());
        exercise.setEmoji(updateExercerRequest.getEmoji());
        exercise.setIsActive(updateExercerRequest.getIsActive());

        // cập nhật exercise
        return repository.save(exercise);
    }

    @Override
    @Transactional
    public void deactivateExercise(Integer exerciseID) {
        repository.deactivateById(exerciseID);
    }

    @Override
    public TagExercise addTag(Integer exerciseID, Integer tagID) {
        // Tìm exercise 
        Exercise exercise = repository.findById(exerciseID)
                .orElseThrow(() -> new EntityNotFoundException("Exercise not found"));
        //Tìm Tag
        Tag tag = tagRepository.findById(tagID)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found"));

        // Tạo ExerciseTag
        TagExercise exerciseTag = new TagExercise(tag, exercise);

        //gọi repository để lưu 
        return exerciseTagRepository.save(exerciseTag);
    }

    @Override
    @Transactional
    public void deleteTag(Integer exerciseID, Integer tagID) {
        exerciseTagRepository.deleteByExercise_ExerciseIDAndTag_TagID(exerciseID, tagID);
    }

}
