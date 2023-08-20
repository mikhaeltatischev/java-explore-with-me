package ru.practicum.explorewithme.location.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.location.model.Location;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("SELECT l FROM locations AS l " +
            "WHERE (l.lat = ?1 " +
            "AND l.lon = ?2) " +
            "OR ((?2 - l.lat) * (?2 - l.lat)) + ((?1 - l.lon) * (?1 - l.lon)) < l.radius * l.radius")
    Location checkLocation(Double lat, Double lon);

    List<Location> getLocationBy(PageRequest pageRequest);

    List<Location> getLocationByAddressContainingIgnoreCaseOrNameContainingIgnoreCase(String address, String name, PageRequest pageRequest);
}