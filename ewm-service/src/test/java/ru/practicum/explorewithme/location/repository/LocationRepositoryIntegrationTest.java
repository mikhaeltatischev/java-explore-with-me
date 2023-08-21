package ru.practicum.explorewithme.location.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.explorewithme.location.model.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql(value = "/set-up.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class LocationRepositoryIntegrationTest {

    @Autowired
    private LocationRepository repository;

    @Test
    public void checkLocationWhenMethodInvokeReturnLocation() {
        Location location = repository.checkLocation(1.0, 2.0);
        assertEquals("Памятник святому", location.getName());
    }

    @Test
    public void getLocationByWhenMethodInvokeReturnLocations() {
        assertEquals(2, repository.getLocationBy(PageRequest.of(0, 10)).size());
    }

    @Test
    public void getLocationByAddressContainingIgnoreCaseOrNameContainingIgnoreCaseByWhenMethodInvokeReturnLocations() {
        assertEquals(1, repository.getLocationByAddressContainingIgnoreCaseOrNameContainingIgnoreCase("Улица свободы 21", "Памятник святому", PageRequest.of(0, 10)).size());
    }
}