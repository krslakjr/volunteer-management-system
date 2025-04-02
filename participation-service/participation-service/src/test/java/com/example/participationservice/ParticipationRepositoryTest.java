package com.example.participationservice;

import com.example.participationservice.models.Participation;
import com.example.participationservice.repository.ParticipationRepository;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import jakarta.persistence.EntityManager;

import jakarta.persistence.PersistenceContext;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ParticipationRepositoryTest {

    @Autowired
    private ParticipationRepository participationRepository;

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
        List<Participation> permissions = participationRepository.findAll();
        long queryCount = statistics.getQueryExecutionCount();
        System.out.println("Executed queries: " + queryCount);
        assertTrue(queryCount <= 2, "N+1 problem detektovan! Previše upita je izvršeno.");
    }
}
