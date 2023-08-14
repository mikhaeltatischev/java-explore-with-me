package ru.practicum.explorewithme.user.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql(value = "/set-up.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void findAllByIdInWhenMethodInvokeReturnUsers() {
        assertEquals(2, repository.findAllByIdIn(List.of(1L, 2L), PageRequest.of(0, 10)).size());
    }

    @Test
    public void findAllByWhenMethodInvokeReturnUsers() {
        assertEquals(2, repository.findAllBy(PageRequest.of(0, 10)).size());
    }
}