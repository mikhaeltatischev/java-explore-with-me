package ru.practicum.explorewithme.user.dto;

import ru.practicum.explorewithme.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDto toDto(User user) {
        Long id = user.getId();
        String email = user.getEmail();
        String name = user.getName();

        return new UserDto(id, email, name);
    }

    public static User toUser(UserDto user) {
        Long id = user.getId();
        String email = user.getEmail();
        String name = user.getName();

        return new User(id, email, name);
    }

    public static List<UserDto> toDto(List<User> users) {
        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<User> toUser(List<UserDto> users) {
        return users.stream()
                .map(UserMapper::toUser)
                .collect(Collectors.toList());
    }

    public static UserShortDto toShortDto(User user) {
        Long id = user.getId();
        String name = user.getName();

        return new UserShortDto(id, name);
    }
}