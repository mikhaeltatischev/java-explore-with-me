package ru.practicum.explorewithme.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    List<EndpointHit> findAllByTimestampIsAfterAndTimestampIsBeforeAndUriIsIn(LocalDateTime timestamp, LocalDateTime timestamp2, Collection<String> uri);

    List<EndpointHit> findAllByTimestampIsAfterAndTimestampIsBefore(LocalDateTime timestamp, LocalDateTime timestamp2);
}