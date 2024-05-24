/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Duc Huy
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DailyRecord")
public class DailyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RecordID")
    private Integer recordID;

    @Column(name = "totalCaloriesIn")
    private Integer totalCaloriesIn;

    @Column(name = "TotalCaloriesOut")
    private Integer totalCaloriesOut;

    @Column(name = "Date")
    private LocalDate date;

    @Column(name = "MemberID")
    private Integer memberID;

    public DailyRecord(LocalDate date, Integer memberID) {
        totalCaloriesIn = 0;
        totalCaloriesOut = 0;
        this.date = date;
        this.memberID = memberID;
    }

}
