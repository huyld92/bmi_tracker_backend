/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.fu.bmi_tracker.services;

import com.fu.bmi_tracker.model.entities.DailyRecord;
import com.fu.bmi_tracker.payload.response.DailyRecordFullResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Duc Huy
 */
public interface DailyRecordService extends GeneralService<DailyRecord> {

    Optional<DailyRecord> findByMemberIDAndDate(Integer memberID, LocalDate date);

    Optional<DailyRecord> findByAccountIDAndDate(Integer accountID, LocalDate date);

    public List<DailyRecord> getDailyRecordsForWeek(Integer memberID, LocalDate date);

    public List<DailyRecordFullResponse> Last7DaysByMemberID(Integer memberID, LocalDate endDate);

}
