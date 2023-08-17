package ru.practicum.explorewithme.compilation.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithme.compilation.dto.UpdateCompilationRequest;
import ru.practicum.explorewithme.compilation.service.CompilationService;
import ru.practicum.explorewithme.event.model.Event;

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
    private NewCompilationDto newCompilationDto;
    private UpdateCompilationRequest updateRequest;
    private long compId;

    @BeforeEach
    public void setUp() {
        compId = 1L;
        compilationDto = CompilationDto.builder()
                .id(compId)
                .build();
        compilations = List.of(compilationDto);

        updateRequest = new UpdateCompilationRequest();

        newCompilationDto = NewCompilationDto.builder()
                .title("Compilation")
                .build();

        compilationDto = CompilationDto.builder()
                .title("Compilation")
                .build();
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

    @Test
    public void createCompilationWhenMethodInvokeReturnCompilation() {
        when(compilationService.createCompilation(newCompilationDto)).thenReturn(compilationDto);

        assertEquals(compilationDto, controller.createCompilation(newCompilationDto));
    }

    @Test
    public void deleteCompilationWhenMethodInvokeReturnCompilation() {
        when(compilationService.deleteCompilation(compId)).thenReturn(compilationDto);

        assertEquals(compilationDto, controller.deleteCompilation(compId));
    }

    @Test
    public void updateCompilationWhenMethodInvokeReturnCompilation() {
        when(compilationService.updateCompilation(compId, updateRequest)).thenReturn(compilationDto);

        assertEquals(compilationDto, controller.updateCompilation(compId, updateRequest));
    }
}