package ru.practicum.explorewithme.location.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import ru.practicum.explorewithme.location.dto.LocationDto;
import ru.practicum.explorewithme.location.dto.LocationUpdateRequest;
import ru.practicum.explorewithme.location.model.Location;
import ru.practicum.explorewithme.location.repository.LocationRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.practicum.explorewithme.location.dto.LocationMapper.toDto;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository repository;

    @InjectMocks
    private LocationServiceImpl locationService;

    private Location location;
    private LocationUpdateRequest updateRequest;
    private long locationId;

    @BeforeEach
    public void setUp() {
        locationId = 1L;

        location = Location.builder()
                .lat(2.2)
                .lon(2.2)
                .radius(0.0)
                .name("name")
                .type("type")
                .address("address")
                .build();

        updateRequest = LocationUpdateRequest.builder()
                .name("same")
                .radius(1.0)
                .build();
    }

    @Test
    public void createLocationWhenMethodInvokeReturnLocation() {
        when(repository.save(location)).thenReturn(location);

        LocationDto locationDto = locationService.createLocation(toDto(location));

        assertEquals(location.getLat(), locationDto.getLat());
        assertEquals(location.getLon(), locationDto.getLon());
        assertEquals(location.getName(), locationDto.getName());
        assertEquals(location.getType(), locationDto.getType());
    }

    @Test
    public void updateLocationWhenMethodInvokeReturnLocation() {
        when(repository.findById(1L)).thenReturn(Optional.of(location));
        when(repository.save(location)).thenReturn(location);

        LocationDto locationDto = locationService.updateLocation(locationId, updateRequest);

        assertEquals(location.getLat(), locationDto.getLat());
        assertEquals(location.getLon(), locationDto.getLon());
        assertEquals(updateRequest.getName(), locationDto.getName());
        assertEquals(updateRequest.getRadius(), locationDto.getRadius());
        assertEquals(location.getType(), locationDto.getType());
    }

    @Test
    public void deleteLocationWhenMethodInvokeReturnLocation() {
        when(repository.findById(1L)).thenReturn(Optional.of(location));

        LocationDto locationDto = locationService.deleteLocation(locationId);

        assertEquals(location.getLat(), locationDto.getLat());
        assertEquals(location.getLon(), locationDto.getLon());
        assertEquals(location.getName(), locationDto.getName());
        assertEquals(location.getType(), locationDto.getType());

        verify(repository).delete(location);
    }

    @Test
    public void findLocationByIdWhenMethodInvokeReturnLocation() {
        when(repository.findById(locationId)).thenReturn(Optional.of(location));

        LocationDto locationDto = locationService.findLocationById(locationId);

        assertEquals(location.getLat(), locationDto.getLat());
        assertEquals(location.getLon(), locationDto.getLon());
        assertEquals(location.getName(), locationDto.getName());
        assertEquals(location.getType(), locationDto.getType());
    }

    @Test
    public void getLocationsWhenMethodInvokeReturnLocations() {
        when(repository.getLocationBy(PageRequest.of(0, 10))).thenReturn(List.of(location));

        List<LocationDto> locations = locationService.getLocations(0, 10);

        assertEquals(1, locations.size());
    }

    @Test
    public void findLocationsWhenMethodInvokeReturnLocations() {
        when(repository.getLocationByAddressContainingIgnoreCaseOrNameContainingIgnoreCase("text", "text", PageRequest.of(0, 10)))
                .thenReturn(List.of(location));

        List<LocationDto> locations = locationService.findLocations("text",  0, 10);

        assertEquals(1, locations.size());
    }
}