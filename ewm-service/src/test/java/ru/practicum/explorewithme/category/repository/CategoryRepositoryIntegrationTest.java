package ru.practicum.explorewithme.category.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql(value = "/set-up.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CategoryRepositoryIntegrationTest {

    @Autowired
    private CategoryRepository repository;

    private PageRequest defaultPageRequest;

    @BeforeEach
    public void setUp() {
        defaultPageRequest = PageRequest.of(0, 10);
    }

    @Test
    public void findAllBy() {
        assertEquals(2, repository.findAllBy(defaultPageRequest).size());
    }
}