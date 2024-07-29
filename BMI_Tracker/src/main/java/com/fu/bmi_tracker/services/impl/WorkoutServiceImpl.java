/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Advisor;
import com.fu.bmi_tracker.model.entities.Exercise;
import com.fu.bmi_tracker.model.entities.Workout;
import com.fu.bmi_tracker.model.entities.WorkoutExercise;
import com.fu.bmi_tracker.payload.request.CreateWorkoutRequest;
import com.fu.bmi_tracker.payload.request.UpdateWorkoutRequest;
import com.fu.bmi_tracker.payload.response.CountWorkoutResponse;
import com.fu.bmi_tracker.repository.AdvisorRepository;
import com.fu.bmi_tracker.repository.ExerciseRepository;
import com.fu.bmi_tracker.repository.WorkoutExerciseRepository;
import com.fu.bmi_tracker.repository.WorkoutRepository;
import com.fu.bmi_tracker.services.WorkoutService;
import com.fu.bmi_tracker.util.ExerciseUtils;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    AdvisorRepository advisorRepository;

    @Autowired
    WorkoutExerciseRepository workoutExerciseRepository;

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
                .orElseThrow(() -> new EntityNotFoundException("Workout id{" + updateWorkoutRequest.getWorkoutID() + "} not found"));

        // kiểm tra weight có được cập nhật
        if (!workout.getStandardWeight().equals(updateWorkoutRequest.getStandardWeight())) {
            // cập nhật lại calories burned khi weight có thay đổi
            workout.setTotalCaloriesBurned(0);
            // duyệt danh sách workout exercise
            workout.getWorkoutExercises().forEach(workoutExercise -> {
                // tính calories burned
                int caloriesBurned = ExerciseUtils.calculateCalories(
                        workoutExercise.getExercise().getMet(),
                        workout.getStandardWeight(),
                        workoutExercise.getDuration());

                // tính totalCaloriesBurned và cập nhât
                int totalCaloriesBurned = workout.getTotalCaloriesBurned() + caloriesBurned;
                workout.setTotalCaloriesBurned(totalCaloriesBurned);

                // cập nhật calories workout exercise khi thay đổi weight
                workoutExercise.setCaloriesBurned(caloriesBurned);

                workoutExerciseRepository.save(workoutExercise);

            });
        }

        // cập nhật thông tin workout
        workout.setWorkoutName(updateWorkoutRequest.getWorkoutName());
        workout.setWorkoutDescription(updateWorkoutRequest.getWorkoutDescription());

//        workout.setIsActive(updateWorkoutRequest.getIsActive());
        // lưu lại thông tin mới và trả Workout
        return workoutRepository.save(workout);
    }

    @Override
    public Iterable<Workout> getWorkoutByAdvisorID(Integer advisorID) {
        return workoutRepository.findByAdvisor_AdvisorID(advisorID);
    }

    @Override
    public List<CountWorkoutResponse> countTotalWorkoutIn6Months() {
        // đếm số workout theo từng tháng trong 6 tháng
        LocalDate startDate = LocalDate.now().minusMonths(6).withDayOfMonth(1);
        LocalDate endDate = LocalDate.now();
        return workoutRepository.countTotalWorkoutPerMonthInBetween(startDate, endDate);
    }

    @Override
    public Workout createNewWorkout(CreateWorkoutRequest createWorkoutRequest, Integer accountID) {
        // tìm advisor
        Advisor advisor = advisorRepository.findByAccount_AccountID(accountID)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        // tạo mới workout
        Workout workout = new Workout(
                createWorkoutRequest.getWorkoutName(),
                createWorkoutRequest.getStandardWeight(),
                createWorkoutRequest.getWorkoutDescription(),
                advisor);

        // tạo workout exercise
        List<WorkoutExercise> workoutExercises = new ArrayList<>();
        createWorkoutRequest.getWorkoutExerciseRequests().forEach(workoutExercise -> {
            // tìm exercise
            Exercise exercise = exerciseRepository.findById(workoutExercise.getExerciseID())
                    .orElseThrow(() -> new EntityNotFoundException("Cannot find exercise with id{" + workoutExercise.getExerciseID() + "}!"));

            // tinhs calories burned
            int caloriesBurned = ExerciseUtils.calculateCalories(
                    exercise.getMet(),
                    workout.getStandardWeight(),
                    workoutExercise.getDuration());
            int totalCaloriesBurned = workout.getTotalCaloriesBurned() + caloriesBurned;
            workout.setTotalCaloriesBurned(totalCaloriesBurned);
            // tạo workout Exercise
            WorkoutExercise we = new WorkoutExercise(workout, exercise, workoutExercise.getDuration(), caloriesBurned);
            workoutExercises.add(we);
        });
        // set workout exercise cho workout và lưu trữ
        workout.setWorkoutExercises(workoutExercises);
        return save(workout);
    }

}
