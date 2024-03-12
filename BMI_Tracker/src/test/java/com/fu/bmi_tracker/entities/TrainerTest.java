/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.entities;

import com.fu.bmi_tracker.model.entities.Trainer;
import com.fu.bmi_tracker.repository.TrainerRepository;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 *
 * @author Duc Huy
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TrainerTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TrainerRepository repository;

    @Test
    public void should_find_no_trainer_if_repository_is_empty() {
        Iterable<Trainer> foods = repository.findAll();

        assertThat(foods).isEmpty();
    }

    @Test
    public void should_store_a_trainer() {
        Trainer trainer = new Trainer(5, 170, 80);

        Trainer trainerResult = repository.save(trainer);

        assertThat(trainerResult).isNotNull();
        assertThat(trainerResult).hasFieldOrPropertyWithValue("accountID", 5);
        assertThat(trainerResult).hasFieldOrPropertyWithValue("height", 170f);
        assertThat(trainerResult).hasFieldOrPropertyWithValue("weight", 80f);
    }

    @Test
    public void testFindAll() {
        // Given
        Trainer trainer1 = new Trainer(5, 175.5f, 70.0f);
        Trainer trainer2 = new Trainer(7, 180.0f, 75.0f);

        entityManager.persist(trainer1);
        entityManager.persist(trainer2);
        // When
        Iterable<Trainer> foundTrainers = repository.findAll();

        // Then
        assertThat(foundTrainers).hasSize(2);
        assertThat(foundTrainers).contains(trainer1, trainer2);
    }

//    @Test
//    public void testFindById() {
//        // Given
//        Trainer trainer1 = new Trainer(5, 175.5f, 70.0f);
//        Trainer trainer2 = new Trainer(7, 180.0f, 75.0f);
//        entityManager.persist(trainer1);
//        entityManager.persist(trainer2);
//
//        // When
//        Optional<Trainer> foundTrainer = repository.findById(2);
//
//        // Then
//        assertThat(foundTrainer).isPresent();
//        assertThat(foundTrainer.get()).isEqualTo(trainer2);
//    }
}
