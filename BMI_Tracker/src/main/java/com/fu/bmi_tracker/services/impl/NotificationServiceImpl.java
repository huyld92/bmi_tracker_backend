/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.services.impl;

import com.fu.bmi_tracker.model.entities.Notification;
import com.fu.bmi_tracker.repository.NotificationRepository;
import com.fu.bmi_tracker.services.NotificationService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public Iterable<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Optional<Notification> findById(Integer id) {
        return notificationRepository.findById(id);
    }

    @Override
    public Notification save(Notification t) {
        return notificationRepository.save(t);
    }

    @Override
    public Iterable<Notification> findByAccountID(Integer accountID) {
        return notificationRepository.findByAccountID(accountID);
    }

    @Override
    public void deleteByID(Integer notificationID) {
        notificationRepository.deleteById(notificationID);
    }

    @Override
    public void markAsReadAll(Integer accountID) {
        notificationRepository.markNotificationsAsReadByAccountID(accountID);
    }

}
