/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.DietaryPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface DietaryPreferenceRepository extends JpaRepository<DietaryPreference, Integer> {

    @Query("SELECT new com.fu.bmi_tracker.payload.response.DietaryPreferenceResponse(d.dietaryPreferenceID, d.dietaryPreferenceName) "
            + "FROM DietaryPreference d")
    public Iterable<DietaryPreference> findAllDietaryPreferencesWithDetails();

}
