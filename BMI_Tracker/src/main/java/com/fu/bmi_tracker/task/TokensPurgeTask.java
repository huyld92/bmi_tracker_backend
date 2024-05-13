/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.task;

import com.fu.bmi_tracker.services.RefreshTokenService;
import jakarta.transaction.Transactional;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author Duc Huy
 */
@Service
@Transactional
public class TokensPurgeTask {

    @Autowired
    RefreshTokenService tokenService;

    @Scheduled(cron = "${purge.cron.expression}")
    public void purgeExpired() {

        tokenService.deleteAllExpiredSince(Instant.now());
    }
}
