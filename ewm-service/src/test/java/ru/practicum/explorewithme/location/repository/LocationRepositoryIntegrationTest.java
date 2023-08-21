package ru.practicum.explorewithme.location.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.explorewithme.location.model.Location;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql(value = "/set-up.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class LocationRepositoryIntegrationTest {

    @Autowired
    private LocationRepository repository;

    @Test
    public void checkLocationWhenMethodInvokeReturnLocation() {
        Location location = repository.checkLocation(1.0, 2.0);

        assertEquals(1.0, location.getLat());
        assertEquals(2.0, location.getLon());
        assertEquals(1.0, location.getRadius());
    }

    @Test
    public void getLocationByWhenMethodInvokeReturnLocations() {
        List<Location> location = repository.getLocationBy(PageRequest.of(0, 10));

        assertEquals(2, location.size());
    }
}