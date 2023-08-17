package ru.practicum.explorewithme.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.explorewithme.user.dto.UserDto;
import ru.practicum.explorewithme.user.model.User;
import ru.practicum.explorewithme.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.practicum.explorewithme.user.dto.UserMapper.toDto;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service = new UserServiceImpl();

    private long userId;
    private int from;
    private int size;
    private List<Long> ids;
    private User user;
    private List<User> users;

    @BeforeEach
    public void setUp() {
        from = 0;
        size = 5;
        userId = 1L;
        ids = List.of(userId);

        user = User.builder()
                .id(userId)
                .email("mail@mail.ru")
                .name("Sasha Popov")
                .build();

        users = List.of(user);
    }

    @Test
    public void getWhenMethodInvokeReturnUsers() {
        when(repository.findAllByIdIn(any(), any())).thenReturn(users);

        List<UserDto> usersDto = service.get(ids, from, size);

        assertEquals(toDto(users), usersDto);
    }

    @Test
    public void addWhenMethodInvokeReturnUser() {
        when(repository.save(user)).thenReturn(user);

        UserDto userDto = service.add(toDto(user));

        assertEquals(toDto(user), userDto);
    }

    @Test
    public void deleteWhenMethodInvokeReturnUser() {
        when(repository.findById(userId)).thenReturn(Optional.of(user));

        UserDto userDto = service.delete(userId);

        assertEquals(toDto(user), userDto);
    }
}