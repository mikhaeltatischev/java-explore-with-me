package ru.practicum.explorewithme.location.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.location.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByLatAndLon(float lat, float lon);
}