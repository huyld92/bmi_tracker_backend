/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.payload.response;

import com.fu.bmi_tracker.model.entities.Notification;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Duc Huy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {

    private Integer notificationID;
    private String content;
    private String title;
    private LocalDateTime createdTime;
    private Boolean isRead;

    public NotificationResponse(Notification notification) {
        this.notificationID = notification.getNotificationID();
        this.content = notification.getContent();
        this.title = notification.getTitle();
        this.createdTime = notification.getCreatedTime();
        this.isRead = notification.getIsRead();

    }
}
