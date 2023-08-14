package ru.practicum.explorewithme.admin;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.category.dto.NewCategoryDto;
import ru.practicum.explorewithme.category.service.CategoryService;
import ru.practicum.explorewithme.category.service.CategoryServiceImpl;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithme.compilation.dto.UpdateCompilationRequest;
import ru.practicum.explorewithme.compilation.service.CompilationService;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.UpdateEventAdminRequest;
import ru.practicum.explorewithme.event.service.EventServiceImpl;
import ru.practicum.explorewithme.user.dto.UserDto;
import ru.practicum.explorewithme.user.service.UserService;
import ru.practicum.explorewithme.user.service.UserServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class AdminControllerTest {

    @Mock
    private final UserService userService = new UserServiceImpl();

    @Mock
    private final CategoryService categoryService = new CategoryServiceImpl();

    @Mock
    private EventServiceImpl eventService;

    @Mock
    private CompilationService compilationService;

    @InjectMocks
    private AdminController controller;

    private UpdateEventAdminRequest eventUpdateRequest;
    private UpdateCompilationRequest updateRequest;
    private List<EventFullDto> events;
    private List<UserDto> users;
    private List<Long> ids;
    private UserDto userDto;
    private CategoryDto categoryDto;
    private NewCategoryDto newCategoryDto;
    private EventFullDto eventFullDto;
    private NewCompilationDto newCompilationDto;
    private CompilationDto compilationDto;
    private long userId;
    private long categoryId;
    private long eventId;
    private long compId;

    @BeforeEach
    public void setUp() {
        userId = 1L;
        compId = 1L;
        categoryId = 1L;
        eventId = 1L;

        updateRequest = new UpdateCompilationRequest();

        eventUpdateRequest = new UpdateEventAdminRequest();

        eventFullDto = EventFullDto.builder()
                .id(eventId)
                .build();

        userDto = UserDto.builder()
                .id(userId)
                .email("mail@mail.ru")
                .name("name name")
                .build();

        categoryDto = CategoryDto.builder()
                .id(categoryId)
                .name("drama")
                .build();

        newCategoryDto = NewCategoryDto.builder()
                .name("drama")
                .build();

        newCompilationDto = NewCompilationDto.builder()
                .title("Compilation")
                .build();

        compilationDto = CompilationDto.builder()
                .title("Compilation")
                .build();

        users = List.of(userDto);
        ids = List.of(userId);
        events = List.of(eventFullDto);
    }

    @Test
    public void getUsersWhenMethodInvokeReturnUsers() {
        when(userService.get(ids, 0 , 10)).thenReturn(users);

        assertEquals(users, controller.getUsers(ids, 0 , 10));
    }

    @Test
    public void createUserWhenMethodInvokeReturnUser() {
        when(userService.add(userDto)).thenReturn(userDto);

        assertEquals(userDto, controller.createUser(userDto));
    }

    @Test
    public void deleteUserWhenMethodInvokeReturnUser() {
        when(userService.delete(userId)).thenReturn(userDto);

        assertEquals(userDto, controller.deleteUser(userId));
    }

    @Test
    public void createCategoryWhenMethodInvokeReturnCategory() {
        when(categoryService.create(newCategoryDto)).thenReturn(categoryDto);

        assertEquals(categoryDto, controller.createCategory(newCategoryDto));
    }

    @Test
    public void deleteCategoryWhenMethodInvokeReturnCategory() {
        when(categoryService.delete(categoryId)).thenReturn(categoryDto);

        assertEquals(categoryDto, controller.deleteCategory(categoryId));
    }

    @Test
    public void updateCategoryWhenMethodInvokeReturnCategory() {
        when(categoryService.update(categoryId, categoryDto)).thenReturn(categoryDto);

        assertEquals(categoryDto, controller.updateCategory(categoryId, categoryDto));
    }

    @Test
    public void findEventsWhenMethodInvokeReturnEvents() {
        when(eventService.findEventsForAdmin(any())).thenReturn(events);

        assertEquals(events, controller.findEvents(List.of(userId), List.of("PUBLISHED"), List.of(1L),
                "2023-08-14 19:13:51", "2024-08-14 19:13:51", 0, 10));
    }

    @Test
    public void editEventWhenMethodInvokeReturnEvent() {
        when(eventService.editEvent(eventId, eventUpdateRequest)).thenReturn(eventFullDto);

        assertEquals(eventFullDto, controller.editEvent(eventId, eventUpdateRequest));
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