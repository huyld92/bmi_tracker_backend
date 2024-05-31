/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Column(name = "DefaultCalories")
    private Integer defaultCalories;

    @Column(name = "Date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "MemberID")
    @JsonIgnore
    private Member member;

    public DailyRecord(LocalDate date, int defaultCalories, Member member) {
        this.totalCaloriesIn = 0;
        this.totalCaloriesOut = 0;
        this.defaultCalories = defaultCalories;
        this.date = date;
        this.member = member;
    }

}
