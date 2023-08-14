package ru.practicum.explorewithme.event.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql(value = "/set-up.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class EventRepositoryIntegrationTest {

    @Autowired
    private EventRepository repository;

    @Test
    public void findAllByInitiatorIdWhenMethodInvokeReturnEvents() {
        assertEquals(1, repository.findAllByInitiatorId(1L, PageRequest.of(0, 10)).size());
    }
}