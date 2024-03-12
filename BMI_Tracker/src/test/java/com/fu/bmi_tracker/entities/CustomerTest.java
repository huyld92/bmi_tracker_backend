/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fu.bmi_tracker.entities;

import com.fu.bmi_tracker.model.entities.Customer;
import com.fu.bmi_tracker.repository.CustomerRepository;
import java.time.Instant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class CustomerTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository repository;

    @Test
    public void should_find_no_customer_if_repository_is_empty() {
        Iterable<Customer> foods = repository.findAll();

        assertThat(foods).isEmpty();
    }

    @Test
    public void should_store_a_customer() {
        // Create a new customer
        Customer customer = new Customer();
        customer.setAccountID(2); // Set account ID
        customer.setGoalID(1); // Set goal ID
        customer.setTargetWeight(70); // Set target weight
        customer.setTdee(2000); // Set TDEE
        customer.setCalories(1800); // Set calories
        customer.setPrivate(false); // Set privacy status
        customer.setLastUpdatedTime(Instant.now()); // Set last updated time

        Customer customerResult = repository.save(customer);

        // Verify that the customer is saved successfully
        assertThat(customerResult).isNotNull();
        assertThat(customerResult.getCustomerID()).isNotNull();
        assertEquals(customer.getAccountID(), customerResult.getAccountID());
    }

    @Test
    public void testFindAll() {
        // Create a new customer
        Customer customer1 = new Customer();
        customer1.setAccountID(2); // Set account ID
        customer1.setGoalID(1); // Set goal ID
        customer1.setTargetWeight(70); // Set target weight
        customer1.setTdee(2000); // Set TDEE
        customer1.setCalories(1800); // Set calories
        customer1.setPrivate(false); // Set privacy status
        customer1.setLastUpdatedTime(Instant.now()); // Set last updated time
        Customer customer2 = new Customer(3, 1, 80, 1500, 1800, false, Instant.now());

        entityManager.persist(customer1);
        entityManager.persist(customer2);
        // When
        Iterable<Customer> foundCustomers = repository.findAll();

        // Then
        assertThat(foundCustomers).hasSize(2);
        assertThat(foundCustomers).contains(customer1, customer2);
    }
//
//    private void resetIdSequence(String idColumnName, String tableName) {
//        String resetSequenceQuery = "DBCC CHECKIDENT ('" + tableName + "', RESEED, 0)";
//        jdbcTemplate.execute(resetSequenceQuery);
//    }
}
