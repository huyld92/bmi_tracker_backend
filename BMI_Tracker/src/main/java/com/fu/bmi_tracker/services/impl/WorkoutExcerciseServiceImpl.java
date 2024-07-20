/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Exercise;
import com.fu.bmi_tracker.model.entities.Workout;
import com.fu.bmi_tracker.model.entities.WorkoutExercise;
import com.fu.bmi_tracker.payload.request.CreateWorkoutExerciseRequest;
import com.fu.bmi_tracker.repository.ExerciseRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.repository.WorkoutExerciseRepository;
import com.fu.bmi_tracker.repository.WorkoutRepository;
import com.fu.bmi_tracker.services.WorkoutExerciseService;
import com.fu.bmi_tracker.util.ExerciseUtils;
import jakarta.persistence.EntityNotFoundException;

@Service
public class WorkoutExcerciseServiceImpl implements WorkoutExerciseService {

    @Autowired
    WorkoutExerciseRepository workoutExerciseRepository;

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Override
    public Iterable<WorkoutExercise> findAll() {
        return workoutExerciseRepository.findAll();
    }

    @Override
    public Optional<WorkoutExercise> findById(Integer id) {
//        return workoutExerciseRepository.findById(id);
        return null;
    }

    @Override
    public WorkoutExercise save(WorkoutExercise t) {
        return workoutExerciseRepository.save(t);
    }

    @Override
    public List<WorkoutExercise> saveAll(List<WorkoutExercise> workoutExercises) {
        return workoutExerciseRepository.saveAll(workoutExercises);
    }

    @Override
    public void deleteWorkoutExercise(Integer workoutExerciseID) {
        WorkoutExercise workoutExercise = workoutExerciseRepository.findById(workoutExerciseID)
                .orElseThrow(() -> new EntityNotFoundException("Workout exercise id{" + workoutExerciseID + "} not found"));

        // cập nhật calories burned khi deavtivate exercise
        Workout workout = workoutExercise.getWorkout();
        int totalCorlories = workout.getTotalCloriesBurned() - workoutExercise.getCaloriesBurned();
        workout.setTotalCloriesBurned(totalCorlories);
        workoutRepository.save(workout);

        // xóa workout exercise 
        workoutExerciseRepository.delete(workoutExercise);
    }

    @Override
    public WorkoutExercise createWorkoutExercise(CreateWorkoutExerciseRequest workoutExerciseRequest) {
        // Gọi workoutRepository tìm workout
        Workout workout = workoutRepository.findById(workoutExerciseRequest.getWorkoutID())
                .orElseThrow(() -> new EntityNotFoundException("Workout not found"));
        // Gọi exerciseRepository tim exercise
        Exercise exercise = exerciseRepository.findById(workoutExerciseRequest.getExerciseID())
                .orElseThrow(() -> new EntityNotFoundException("Exercise not found"));

        // tính calories burned
        int caloriesBurned = ExerciseUtils.calculateCalories(
                exercise.getMet(),
                workout.getStandardWeight(),
                workoutExerciseRequest.getDuration());

        // cập nhật calories của workout
        int totalWorkout = workout.getTotalCloriesBurned() + caloriesBurned;
        workout.setTotalCloriesBurned(totalWorkout);
        workoutRepository.save(workout);

        // Khởi tạo WorkoutExercise
        WorkoutExercise workoutExercise = new WorkoutExercise(
                workout,
                exercise,
                workoutExerciseRequest.getDuration(),
                caloriesBurned);
        return workoutExerciseRepository.save(workoutExercise);
    }

    @Override
    public void deactivateWorkoutExercise(Integer workoutExerciseID) {
        WorkoutExercise workoutExercise = workoutExerciseRepository.findById(workoutExerciseID)
                .orElseThrow(() -> new EntityNotFoundException("Workout exercise id{" + workoutExerciseID + "} not found"));

        // cập nhật calories burned khi deavtivate exercise
        Workout workout = workoutExercise.getWorkout();
        int totalCorlories = workout.getTotalCloriesBurned() - workoutExercise.getCaloriesBurned();
        workout.setTotalCloriesBurned(totalCorlories);
        workoutRepository.save(workout);

        // cập nhật workout exercise
        workoutExercise.setIsActive(Boolean.FALSE);
        workoutExerciseRepository.save(workoutExercise);
    }
}
