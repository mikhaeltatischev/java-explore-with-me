package ru.practicum.explorewithme.user.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.user.dto.UserDto;
import ru.practicum.explorewithme.user.exception.UserNotFoundException;
import ru.practicum.explorewithme.user.model.User;
import ru.practicum.explorewithme.user.repository.UserRepository;

import java.util.List;

import static ru.practicum.explorewithme.user.dto.UserMapper.toDto;
import static ru.practicum.explorewithme.user.dto.UserMapper.toUser;

@Slf4j
@Service
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Override
    public List<UserDto> get(List<Long> ids, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<User> foundUsers = findUsers(ids, pageRequest);

        log.info("Found users: " + foundUsers);

        return toDto(foundUsers);
    }

    @Override
    public UserDto add(UserDto user) {
        User savedUser = repository.save(toUser(user));

        log.info("Saved user: " + savedUser);

        return toDto(savedUser);
    }

    @Override
    public UserDto delete(long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        repository.delete(user);

        log.info("User with id: " + id + " deleted");

        return toDto(user);
    }

    private List<User> findUsers(List<Long> ids, PageRequest pageRequest) {
        if (ids != null) {
            return repository.findAllByIdIn(ids, pageRequest);
        } else {
            return repository.findAllBy(pageRequest);
        }
    }
}