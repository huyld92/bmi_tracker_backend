/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.entities;

import com.fu.bmi_tracker.model.entities.Limitation;
import com.fu.bmi_tracker.model.entities.Tag;
import com.fu.bmi_tracker.model.entities.TagLimitation;
import com.fu.bmi_tracker.repository.LimitationRepository;
import java.util.ArrayList;
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
public class LimitationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LimitationRepository repository;

    @Test
    public void should_find_no_limitation_if_repository_is_empty() {
        Iterable<Limitation> limitations = repository.findAll();

        assertThat(limitations).isEmpty();
    }

    @Test
    public void should_store_a_limitation() {
        // Given
        Limitation limitation = new Limitation();
        limitation.setLimitationName("Time limit");
        limitation.setDescription("Limitation on time duration");
        limitation.setStatus("Active");

        // When
        Limitation savedLimitation = repository.save(limitation);

        // Then
        assertThat(savedLimitation).isNotNull();
        assertThat(savedLimitation.getLimitationID()).isGreaterThan(0);
    }

    @Test
    public void should_store_a_limitation_with_tags() {
        // Given
        Tag tag1 = new Tag();
        tag1.setTagName("Health");
        tag1.setStatus("Active");

        Tag tag2 = new Tag();
        tag2.setTagName("Fitness");
        tag2.setStatus("Active");

        Limitation limitation = new Limitation();
        limitation.setLimitationName("Time limit");
        limitation.setDescription("Limitation on time duration");
        limitation.setStatus("Active");
        limitation.setTagLimitations(new ArrayList<>());

        TagLimitation tagLimitation1 = new TagLimitation();
        tagLimitation1.setTag(tag1);
        tagLimitation1.setLimitation(limitation);

        TagLimitation tagLimitation2 = new TagLimitation();
        tagLimitation2.setTag(tag2);
        tagLimitation2.setLimitation(limitation);

        limitation.getTagLimitations().add(tagLimitation1);
        limitation.getTagLimitations().add(tagLimitation2);

        // When
        Limitation savedLimitation = repository.save(limitation);

        // Then
        assertThat(savedLimitation).isNotNull();
        assertThat(savedLimitation.getLimitationID()).isGreaterThan(0);
        assertThat(savedLimitation.getTagLimitations()).isNotEmpty();
        assertThat(savedLimitation.getTagLimitations()).hasSize(2);
    }

    @Test
    public void should_find_all_limitations() {
        // Given
        Limitation limitation1 = new Limitation();
        limitation1.setLimitationName("Time limit");
        limitation1.setDescription("Limitation on time duration");
        limitation1.setStatus("Active");
        repository.save(limitation1);

        Limitation limitation2 = new Limitation();
        limitation2.setLimitationName("Age limit");
        limitation2.setDescription("Limitation on age");
        limitation2.setStatus("Active");
        repository.save(limitation2);

        // When
        Iterable<Limitation> limitations = repository.findAll();

        // Then
        assertThat(limitations).isNotEmpty();
        assertThat(limitations).hasSize(2);
    }
 
//    @Test
//    public void should_find_limitation_by_id() {
//        Limitation limitation1 = new Limitation("Limitation1", "photo1.jpg", "grams", 100, "Type1", "Active");
//        entityManager.persist(limitation1);
//
//        Limitation limitation2 = new Limitation("Limitation2", "photo2.jpg", "grams", 200, "Type2", "Active");
//        entityManager.persist(limitation2);
//
//        Limitation foundLimitation = repository.findById(limitation2.getLimitationID()).get();
//
//        assertThat(foundLimitation).isEqualTo(limitation2);
//    }
}
