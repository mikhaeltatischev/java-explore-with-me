package ru.practicum.explorewithme.compilation.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql(value = "/set-up.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CompilationRepositoryIntegrationTest {

    private final CompilationRepository repository;

    @Test
    public void getCompilationByPinnedWhenMethodInvokeReturnCompilation() {
        assertEquals(1, repository.getCompilationByPinned(true, PageRequest.of(0, 10)).size());
    }

    @Test
    public void getCompilationByWhenMethodInvokeReturnCompilation() {
        assertEquals(2, repository.getCompilationBy(PageRequest.of(0, 10)).size());
    }

    @Test
    public void getCompilationByWhenSizeIsOneReturnCompilation() {
        assertEquals(1, repository.getCompilationBy(PageRequest.of(0, 1)).size());
    }
}