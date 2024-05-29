/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.ActivityLevel;
import com.fu.bmi_tracker.payload.response.ActivityLevelResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface ActivityLevelRepository extends JpaRepository<ActivityLevel, Object> {

    @Query("SELECT new com.fu.bmi_tracker.payload.response.ActivityLevelResponse(a.activityLevelID, a.activityLevelName) "
            + "FROM ActivityLevel a")
    public List<ActivityLevelResponse> findAllActivityLevelsWithDetails();

}
