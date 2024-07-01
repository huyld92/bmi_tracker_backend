/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.model.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
@Table(name = "Workout")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WorkoutID", nullable = false)
    private Integer workoutID;

    @Column(name = "WorkoutName", nullable = false)
    private String workoutName;

    @Column(name = "WorkoutDescription", nullable = false)
    private String workoutDescription;

    @Column(name = "TotalCloriesBurned", nullable = false)
    private Integer totalCloriesBurned;

    @Column(name = "IsActive", nullable = false)
    private Boolean isActive;

    @Column(name = "AdvisorID", nullable = false)
    private Integer advisorID;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkoutExercise> workoutExercises;

    @ManyToMany
    @JoinTable(
            name = "TagWorkout",
            joinColumns = @JoinColumn(name = "WorkoutID"),
            inverseJoinColumns = @JoinColumn(name = "TagID")
    )
    private List<Tag> tags;

    //Create new workout
    public Workout(String workoutName, String workoutDescription, Integer totalCloriesBurned, Integer advisorID) {
        this.workoutName = workoutName;
        this.workoutDescription = workoutDescription;
        this.totalCloriesBurned = totalCloriesBurned;
        this.advisorID = advisorID;
        this.isActive = true;
    }

}
