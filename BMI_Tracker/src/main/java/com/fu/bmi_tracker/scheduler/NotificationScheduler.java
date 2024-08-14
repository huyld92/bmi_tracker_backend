/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.scheduler;

import com.fu.bmi_tracker.model.entities.DailyRecord;
import com.fu.bmi_tracker.model.entities.Member;
import com.fu.bmi_tracker.model.entities.MemberBodyMass;
import com.fu.bmi_tracker.model.entities.Notification;
import com.fu.bmi_tracker.repository.DailyRecordRepository;
import com.fu.bmi_tracker.repository.MemberBodyMassRepository;
import com.fu.bmi_tracker.repository.MemberRepository;
import com.fu.bmi_tracker.repository.NotificationRepository;
import com.fu.bmi_tracker.services.impl.NotificationServiceImpl;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author BaoLG
 */
@Service
@Transactional
public class NotificationScheduler {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationServiceImpl notificationServiceImpl;

    @Autowired
    DailyRecordRepository dailyRecordRepository;

    @Autowired
    MemberBodyMassRepository memberBodyMassRepository;

    @Scheduled(cron = "${purge.cron.remindsubcriptionnotify}")
    public void sendRemindSubcriptionNotify() {

        List<Member> memberList = memberRepository.findAll();
        if (!memberList.isEmpty()) {
            for (int i = 0; i < memberList.size(); i++) {
                if (memberList.get(i).getEndDateOfPlan() != null) {
                    LocalDate currentDate = LocalDate.now();
                    LocalDate memberPackageEndDate = memberList.get(i).getEndDateOfPlan();
                    //Neu hom nay la 7 ngay truoc khi het han
                    if (currentDate.isEqual(memberPackageEndDate.minusDays(7))) {

                        String memberName = memberList.get(i).getAccount().getFullName();
                        //Tao thong tin cho reminder Notify
                        String titile = "Your subcription have 7 days left.";
                        String body = memberName + "'s subcription is end at " + memberPackageEndDate + " Please extend your subcription if you want continue using the package";
                        String deviceToken = memberList.get(i).getAccount().getDeviceToken();
                        //Sao luu Notification 
                        
                        
                        Notification notify = new Notification();
                        notify.setAccountID(memberList.get(i).getAccount().getAccountID());
                        notify.setTitle(titile);
                        notify.setContent(body);
                        notify.setCreatedTime(LocalDateTime.now());
                        notify.setIsRead(Boolean.FALSE);
                        notificationRepository.save(notify);

                        //Send notify to Account that have token (Still login)
                        if (deviceToken != null) {
                            notificationServiceImpl.sendNotification(titile, body, deviceToken);
                        }

                    }
                }
            }
        }
    }

    @Scheduled(cron = "${purge.cron.remindrecordnotify}")
    public void sendRemindDailyReordNotify() {
        List<DailyRecord> dailyRecordList = dailyRecordRepository.findByDate(LocalDate.now());
        if (!dailyRecordList.isEmpty()) {
            for (int i = 0; i < dailyRecordList.size(); i++) {
                DailyRecord record = dailyRecordList.get(i);
                String deviceToken = dailyRecordList.get(i).getMember().getAccount().getDeviceToken();
                if (record.getTotalCaloriesIn() == 0) {
                    //Tao thong tin cho reminder Notify
                    String titile = "You forgot input today record.";
                    String body = "For accurate calculation, please enter your record today.";
                    //Sao luu Notification 
                    Notification notify = new Notification();
                    notify.setAccountID(dailyRecordList.get(i).getMember().getAccount().getAccountID());
                    notify.setTitle(titile);
                    notify.setContent(body);
                    notify.setCreatedTime(LocalDateTime.now());
                    notify.setIsRead(Boolean.FALSE);
                    notificationRepository.save(notify);
                    if (deviceToken != null) {
                        notificationServiceImpl.sendNotification(titile, body, deviceToken);
                    }
                }
            }
        }
    }

    @Scheduled(cron = "${purge.cron.remindsubcriptionnotify}")
    public void sendRemindBodyMassReordNotify() {
        List<Member> memberList = memberRepository.findAll();
        if (!memberList.isEmpty()) {
            for (int i = 0; i < memberList.size(); i++) {
                Optional<MemberBodyMass> memberBodyMass = memberBodyMassRepository.findLatestByAccountID(memberList.get(i).getAccount().getAccountID());
                if (!memberBodyMass.isPresent()) {

                    //Neu lan cuoi cap nhat Body Mass truoc ngay hien tai 7 ngay thi send Notify
                    if (memberBodyMass.get().getDateInput().toLocalDate().isBefore(LocalDate.now().minusDays(7))) {

                        String titile = "Your subcription have 7 days left.";
                        String body = "You have not update your Body Mass in 7 day, Please update it to ensure calculate is accurate";
                        String deviceToken = memberList.get(i).getAccount().getDeviceToken();

                        Notification notify = new Notification();
                        notify.setAccountID(memberList.get(i).getAccount().getAccountID());
                        notify.setTitle(titile);
                        notify.setContent(body);
                        notify.setCreatedTime(LocalDateTime.now());
                        notify.setIsRead(Boolean.FALSE);
                        notificationRepository.save(notify);

                        if (deviceToken != null) {
                            notificationServiceImpl.sendNotification(titile, body, deviceToken);
                        }
                    }
                }
            }
        }

    }
}
