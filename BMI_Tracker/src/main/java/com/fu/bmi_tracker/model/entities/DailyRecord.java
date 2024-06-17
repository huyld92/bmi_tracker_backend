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
    @Column(name = "RecordID", nullable = false)
    private Integer recordID;

    @Column(name = "TotalCaloriesIn", nullable = false)
    private Integer totalCaloriesIn;

    @Column(name = "TotalCaloriesOut", nullable = false)
    private Integer totalCaloriesOut;

    @Column(name = "DefaultCalories", nullable = false)
    private Integer defaultCalories;

    @Column(name = "Date", nullable = false)
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

    public DailyRecord(Integer totalCaloriesIn, Integer totalCaloriesOut, Integer defaultCalories, LocalDate date, Member member) {
        this.totalCaloriesIn = totalCaloriesIn;
        this.totalCaloriesOut = totalCaloriesOut;
        this.defaultCalories = defaultCalories;
        this.date = date;
        this.member = member;
    }
    
    

}
