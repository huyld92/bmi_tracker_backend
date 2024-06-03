/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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
@Table(name = "MemberBodyMass")
public class MemberBodyMass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MemberBodyMassID")
    private Integer memberBodyMassID;

    @Column(name = "Height")
    private Integer height;

    @Column(name = "Weight")
    private Integer weight;

    @Column(name = "Age")
    private Integer age;

    @Column(name = "BMI")
    private Double bmi;

    @Column(name = "DateInput")
    private LocalDateTime dateInput;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MemberID")
    private Member member;

    public MemberBodyMass(Integer height, Integer weight, Integer age, double bmi, LocalDateTime dateInput, Member member) {
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.bmi = bmi;
        this.dateInput = dateInput;
        this.member = member;
    }

}
