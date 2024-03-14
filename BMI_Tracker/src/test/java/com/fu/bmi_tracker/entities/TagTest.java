package com.fu.bmi_tracker.entities;

import com.fu.bmi_tracker.model.entities.Tag;
import com.fu.bmi_tracker.repository.TagRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Duc Huy
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TagTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TagRepository repository;
    
      @Test
    public void should_find_no_tag_if_repository_is_empty() {
        Iterable<Tag> tags = repository.findAll();

        assertThat(tags).isEmpty();
    }

    @Test
    public void should_store_a_tag() {
        // Given
        Tag tag = new Tag();
        tag.setTagName("Health");
        tag.setStatus("Active");

        // When
        Tag savedTag = repository.save(tag);

        // Then
        assertThat(savedTag).isNotNull();
        assertThat(savedTag.getTagID()).isGreaterThan(0);
    }

    @Test
    public void should_find_all_tags() {
          // Given
        Tag tag1 = new Tag();
        tag1.setTagName("Health");
        tag1.setStatus("Active");
        repository.save(tag1);

        Tag tag2 = new Tag();
        tag2.setTagName("Fitness");
        tag2.setStatus("Active");
        repository.save(tag2);

        // When
        Iterable<Tag> tags = repository.findAll();

        // Then
        assertThat(tags).isNotEmpty();
        assertThat(tags).hasSize(2);
    }
}
