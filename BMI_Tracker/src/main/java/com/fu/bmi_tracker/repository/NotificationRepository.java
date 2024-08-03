/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.repository;

import com.fu.bmi_tracker.model.entities.Notification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Duc Huy
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    public Iterable<Notification> findByAccountID(Integer accountID);

    @Modifying//Chú thích này cho biết rằng truy vấn sẽ sửa đổi cơ sở dữ liệu
    @Transactional
    @Query("UPDATE Notification n SET n.isRead = true WHERE n.accountID = :accountID")
    public void markNotificationsAsReadByAccountID(Integer accountID);

}
