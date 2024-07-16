/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import com.fu.bmi_tracker.payload.request.CreateExerciseRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
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
@Table(name = "Exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExerciseID", nullable = false)
    private Integer exerciseID;

    @Column(name = "ExerciseName", nullable = false)
    private String exerciseName;

    @Column(name = "Emoji", nullable = true)
    private String emoji;

    @Column(name = "Duration", nullable = false)
    private Integer duration;

    @Column(name = "MET", nullable = false)
    private Float met;

    @Column(name = "Distance", nullable = true)
    private Float distance;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "TagExercise",
            joinColumns = @JoinColumn(name = "ExerciseID"),
            inverseJoinColumns = @JoinColumn(name = "TagID")
    )
    private List<Tag> tags;

    public Exercise(CreateExerciseRequest createExerciseRequest, List<Tag> tags) {
        this.exerciseName = createExerciseRequest.getExerciseName();
        this.emoji = createExerciseRequest.getEmoji();
        this.duration = createExerciseRequest.getDuration();
        this.tags = tags;
        this.met = createExerciseRequest.getMet();
        this.distance = createExerciseRequest.getDistance();
        // Mặc định vừa tạo isAtive true
        this.isActive = true;
    }
}
