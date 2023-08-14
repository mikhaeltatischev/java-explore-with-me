package ru.practicum.explorewithme.compilation.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.service.CompilationService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompilationControllerTest {

    @Mock
    private CompilationService compilationService;

    @InjectMocks
    private CompilationController controller;

    private List<CompilationDto> compilations;
    private CompilationDto compilationDto;

    @BeforeEach
    public void setUp() {
        compilationDto = new CompilationDto();
        compilations = List.of(compilationDto);
    }

    @Test
    public void getCompilationsWhenMethodInvokeReturnCompilations() {
        when(compilationService.getCompilations(false, 0, 10)).thenReturn(compilations);

        assertEquals(compilations, controller.getCompilations(false, 0, 10));
    }

    @Test
    public void findCompilationWhenMethodInvokeReturnCompilation() {
        when(compilationService.findCompilation(1)).thenReturn(compilationDto);

        assertEquals(compilationDto, controller.findCompilation(1));
    }
}