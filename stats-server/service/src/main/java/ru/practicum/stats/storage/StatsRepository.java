package ru.practicum.stats.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.stats.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    List<EndpointHit> findAllByTimestampIsAfterAndTimestampIsBeforeAndUriIsIn(LocalDateTime timestamp, LocalDateTime timestamp2, List<String> uri);

    List<EndpointHit> findAllByTimestampIsAfterAndTimestampIsBefore(LocalDateTime timestamp, LocalDateTime timestamp2);

    @Query("SELECT DISTINCT(e) FROM EndpointHit AS e " +
            "WHERE e.timestamp > ?1 " +
            "AND e.timestamp < ?2 " +
            "AND e.uri IN ?3")
    List<EndpointHit> findAllUniqueAndByUri(LocalDateTime timestamp, LocalDateTime timestamp2, List<String> uri);

    @Query("SELECT DISTINCT(e) FROM EndpointHit AS e " +
            "WHERE e.timestamp > ?1 " +
            "AND e.timestamp < ?2")
    List<EndpointHit> findAllUnique(LocalDateTime timestamp, LocalDateTime timestamp2);

    List<EndpointHit> findAllByIpAndUri(String ip, String uri);
}