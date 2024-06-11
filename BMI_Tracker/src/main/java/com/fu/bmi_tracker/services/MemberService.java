/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Exercise;
import com.fu.bmi_tracker.model.entities.Food;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.Menu;
import com.fu.bmi_tracker.model.entities.Workout;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Duc Huy
 */
public interface MemberService extends GeneralService<Member> {

    public boolean existsByAccountID(int accountID);

    public Optional<Member> findByAccountID(int accountID);

    public List<Exercise> getllExerciseResponseInWorkout(Integer accountID);

    public Menu getMenuSuggestion(int defaultCalories, String dietaryPreference);

    public Workout getWorkoutSuggestion(String classifyBMI);

    public Page<Food> getPaginatedFoodWithPriority(Integer accountID, Pageable pageable);

    public Page<Exercise> getPaginatedExerciseWithPriority(Integer accountID, Pageable pageable);

}
