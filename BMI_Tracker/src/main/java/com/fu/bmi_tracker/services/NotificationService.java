/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.Notification;

/**
 *
 * @author Duc Huy
 */
public interface NotificationService extends GeneralService<Notification> {

    public Iterable<Notification> findByAccountID(Integer accountID);

    public void deleteByID(Integer notificationID);

}
