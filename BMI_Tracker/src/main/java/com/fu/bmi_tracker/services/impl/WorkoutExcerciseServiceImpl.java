/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Exercise;
import com.fu.bmi_tracker.model.entities.Workout;
import com.fu.bmi_tracker.model.entities.WorkoutExercise;
import com.fu.bmi_tracker.repository.ExerciseRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.repository.WorkoutExerciseRepository;
import com.fu.bmi_tracker.repository.WorkoutRepository;
import com.fu.bmi_tracker.services.WorkoutExerciseService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;

@Service
public class WorkoutExcerciseServiceImpl implements WorkoutExerciseService {

    @Autowired
    WorkoutExerciseRepository repository;

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Override
    public Iterable<WorkoutExercise> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<WorkoutExercise> findById(Integer id) {
//        return repository.findById(id);
        return null;
    }

    @Override
    public WorkoutExercise save(WorkoutExercise t) {
        return repository.save(t);
    }

    @Override
    public List<WorkoutExercise> saveAll(List<WorkoutExercise> workoutExercises) {
        return repository.saveAll(workoutExercises);
    }

    @Override
    public List<Exercise> getAllExerciseByWorkoutID(Integer workoutID) {
        return repository.findExerciseByWorkout_WorkoutID(workoutID);
    }

    @Override
    @Transactional
    public void deleteWorkoutExercise(Integer workoutID, Integer exerciseID) {
        repository.deleteByWorkout_WorkoutIDAndExercise_ExerciseID(workoutID, exerciseID);
    }

    @Override
    public WorkoutExercise createWorkoutExercise(Integer workoutID, Integer exerciseID) {
        // Gọi workoutRepository tìm workout
        Workout workout = workoutRepository.findById(workoutID)
                .orElseThrow(() -> new EntityNotFoundException("Workout not found"));
        // Gọi exerciseRepository tim exercise
        Exercise exercise = exerciseRepository.findById(exerciseID)
                .orElseThrow(() -> new EntityNotFoundException("Exercise not found"));
        // Khởi tạo WorkoutExercise
        WorkoutExercise workoutExercise = new WorkoutExercise(workout, exercise, true);
        return repository.save(workoutExercise);
    }

    @Override
    public List<WorkoutExercise> createWorkoutExercises(Integer workoutID, List<Integer> exerciseIDs) {
        // Gọi workoutRepository tìm workout
        Workout workout = workoutRepository.findById(workoutID)
                .orElseThrow(() -> new EntityNotFoundException("Workout not found"));

        List<Exercise> exercises = exerciseRepository.findByExerciseIDIn(exerciseIDs);
        List<WorkoutExercise> workoutExercises = new ArrayList<>();

        for (Exercise exercise : exercises) {
            workoutExercises.add(new WorkoutExercise(workout, exercise, true));
        }
        return repository.saveAll(workoutExercises);
    }

    @Override
    public void deactivateWorkoutExercise(Integer workoutID, Integer exerciseID) {
        repository.deactivateWorkoutExercise(workoutID, exerciseID);
    }
}
