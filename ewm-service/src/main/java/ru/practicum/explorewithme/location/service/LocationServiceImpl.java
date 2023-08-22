package ru.practicum.explorewithme.location.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.location.dto.LocationDto;
import ru.practicum.explorewithme.location.dto.LocationUpdateRequest;
import ru.practicum.explorewithme.location.exception.LocationNotFoundException;
import ru.practicum.explorewithme.location.model.Location;
import ru.practicum.explorewithme.location.repository.LocationRepository;

import java.util.List;

import static ru.practicum.explorewithme.location.dto.LocationMapper.toDto;
import static ru.practicum.explorewithme.location.dto.LocationMapper.toLocation;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository repository;

    @Override
    public LocationDto createLocation(LocationDto locationDto) {
        Location location = repository.save(toLocation(locationDto));

        log.info("Location: " + location + " saved");

        return toDto(location);
    }

    @Override
    public LocationDto updateLocation(long locationId, LocationUpdateRequest updateRequest) {
        Location location = findLocation(locationId);
        updateLocationFields(location, updateRequest);
        Location updatedLocation = repository.save(location);

        log.info("Location: " + updatedLocation + " updated");

        return toDto(updatedLocation);
    }

    @Override
    public LocationDto deleteLocation(long locationId) {
        Location location = findLocation(locationId);
        repository.delete(location);

        log.info("Location with id: " + locationId + " deleted");

        return toDto(location);
    }

    @Override
    public LocationDto findLocationById(long locationId) {
        Location location = findLocation(locationId);

        log.info("Found location: " + location);

        return toDto(location);
    }

    @Override
    public List<LocationDto> getLocations(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Location> locations = repository.getLocationBy(pageRequest);

        log.info("Found locations: " + locations);

        return toDto(locations);
    }

    @Override
    public List<LocationDto> findLocations(String text, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<Location> locations = repository.getLocationByAddressContainingIgnoreCaseOrNameContainingIgnoreCase(text, text, pageRequest);

        log.info("Found locations: " + locations);

        return toDto(locations);
    }

    private Location findLocation(long locationId) {
        return repository.findById(locationId)
                .orElseThrow(() -> new LocationNotFoundException(locationId));
    }

    private void updateLocationFields(Location location, LocationUpdateRequest updateRequest) {
        if (updateRequest.getRadius() != null) {
            location.setRadius(updateRequest.getRadius());
        }

        if (updateRequest.getName() != null) {
            location.setName(updateRequest.getName());
        }
    }
}