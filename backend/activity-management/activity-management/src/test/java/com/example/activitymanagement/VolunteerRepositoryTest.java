package com.example.activitymanagement;

import com.example.activitymanagement.models.Volunteer;
import com.example.activitymanagement.repository.VolunteerRepository;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import jakarta.persistence.EntityManager;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class VolunteerRepositoryTest {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Statistics statistics;

    @BeforeEach
    void setUp() {
        assertNotNull(entityManager, "TestEntityManager je null!");

        EntityManager em = entityManager.getEntityManager();

        Session session = em.unwrap(Session.class);
        statistics = session.getSessionFactory().getStatistics();
        statistics.clear();
    }

    @Test
    void testLazyLoadingWithoutNPlusOneProblem() {
        List<Volunteer> activities = volunteerRepository.findAll();
        long queryCount = statistics.getQueryExecutionCount();
        System.out.println("Executed queries: " + queryCount);
        assertTrue(queryCount <= 2, "N+1 problem detektovan! Previše upita je izvršeno.");
    }
}
