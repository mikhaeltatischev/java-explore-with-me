package ru.practicum.explorewithme.user.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByIdIn(List<Long> ids, PageRequest pageRequest);

    List<User> findAllBy(PageRequest pageRequest);
}