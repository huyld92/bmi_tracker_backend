/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.payload.request.CreateExerciseRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

/**
 *
 * @author Duc Huy
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction(value = "IsActive = 1")
@Table(name = "Exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExerciseID", nullable = false)
    private Integer exerciseID;

    @Column(name = "ExerciseName", nullable = false)
    private String exerciseName;

    @Column(name = "ExercisePhoto", nullable = true)
    private String exercisePhoto;

    @Column(name = "ExerciseVideo", nullable = true)
    private String exerciseVideo;

    @Column(name = "MET", nullable = false)
    private Float met;

    @Column(name = "ExerciseDescription", nullable = true)
    private String exerciseDescription;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "TagID")
    private Tag tag;

    public Exercise(CreateExerciseRequest createExerciseRequest, Tag tag) {
        this.exerciseName = createExerciseRequest.getExerciseName();
        this.exercisePhoto = createExerciseRequest.getExercisePhoto();
        this.exerciseVideo = createExerciseRequest.getExerciseVideo();
        this.met = createExerciseRequest.getMet();
        this.exerciseDescription = createExerciseRequest.getExerciseDescription();
        this.tag = tag;
        // Mặc định vừa tạo isAtive true
        this.isActive = true;
    }

}
