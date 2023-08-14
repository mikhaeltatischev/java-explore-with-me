package ru.practicum.stats.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.stats.storage.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql(value = {"/set-up-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/set-up-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class StatsRepositoryIntegrationTest {

    @Autowired
    private StatsRepository repository;

    private LocalDateTime start;
    private LocalDateTime end;
    private List<String> uris;

    @BeforeEach
    public void setUp() {
        start = LocalDateTime.of(2000, 10, 10, 10, 10);
        end = LocalDateTime.of(2100, 10, 10, 10, 10);
        uris = List.of("/events/1");
    }

    @Test
    public void findAllByTimestampIsAfterAndTimestampIsBeforeAndUriIsInWhenMethodInvokedReturnHits() {
        assertEquals(2, repository.findAllByTimestampIsAfterAndTimestampIsBeforeAndUriIsIn(start, end, uris).size());
    }

    @Test
    public void findAllByTimestampIsAfterAndTimestampIsBeforeWhenMethodInvokedReturnHits() {
        assertEquals(3, repository.findAllByTimestampIsAfterAndTimestampIsBefore(start, end).size());
    }
}