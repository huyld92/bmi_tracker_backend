/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.UserMenu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface UserMenuRepository extends JpaRepository<UserMenu, UserMenu> {

    public List<UserMenu> findAllByUserUserID(Integer userID);

    public void deleteByUserUserIDAndFoodFoodID(int userID, int foodID);

    public void deleteAllByUserUserID(int userID);

}
