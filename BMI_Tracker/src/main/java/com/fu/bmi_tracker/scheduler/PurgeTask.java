/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.scheduler;

import com.fu.bmi_tracker.services.RefreshTokenService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.fu.bmi_tracker.services.SubscriptionService;

/**
 *
 * @author Duc Huy
 */
@Service
@Transactional
public class PurgeTask {

    @Autowired
    RefreshTokenService tokenService;

    @Autowired
    SubscriptionService subscriptionService;

    @Scheduled(cron = "${purge.cron.expression}")
    public void purgeExpired() {
        tokenService.deleteAllExpiredSince(LocalDateTime.now());
    }

    // cập nhật trạng thái cho subscription PENDING or FINISHED
    @Scheduled(cron = "${purge.cron.subscriptionstatus}")
    public void updateSubscriptionStatus() {
        subscriptionService.updateSubscriptionStatus();
    }
}
