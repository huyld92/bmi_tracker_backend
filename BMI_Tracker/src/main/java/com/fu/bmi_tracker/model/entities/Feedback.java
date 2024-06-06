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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author BaoLG
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FeedBack")
public class Feedback {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FeedbackID")
    private int feedbackID;
    
    @Column(name = "Title")
    private String title;
    
    @Column(name = "Type")
    private String type;
    
    @Column(name = "Description")
    private String description;
    
    @Column(name = "Status")
    private boolean status;
    
    @Column(name = "MemberID")
    private Integer memberID;
  
}
