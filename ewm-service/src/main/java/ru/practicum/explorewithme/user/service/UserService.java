package ru.practicum.explorewithme.user.service;

import ru.practicum.explorewithme.user.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> get(List<Long> ids, int from, int size);

    UserDto add(UserDto user);

    UserDto delete(long id);
}