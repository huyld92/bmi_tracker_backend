/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Menu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    public Iterable<Menu> findByAdvisorID(Integer advisorID);

    @Query("SELECT m FROM Menu m JOIN TagMenu tm ON m.menuID = tm.menu.menuID "
            + "JOIN Tag t ON tm.tag.tagID = t.tagID WHERE t.tagName = :tagName")
    List<Menu> findMenusByTagName(String tagName);

    // ABS(m.totalCalories - :recommendedCalories) biểu thức này tính giá trị chênh lệch của totalCalories
    // và sắp xếp theo ASC và chỉ lấy menu đầu tiên trong danh sách menu advisor hệ thống id 1
    @Query("SELECT m FROM Menu m "
            + "JOIN TagMenu tm ON m.menuID = tm.menu.menuID"
            + " JOIN Tag t ON tm.tag.tagID = t.tagID WHERE t.tagName = :tagName AND m.advisorID = 1"
            + " ORDER BY ABS(m.totalCalories - :recommendedCalories) ASC LIMIT 1")
    Menu findTopByTagNameAndClosestCalories(String tagName, int recommendedCalories);

}
