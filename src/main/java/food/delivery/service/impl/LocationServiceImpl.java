package food.delivery.service.impl;

import food.delivery.dto.LocationDto;
import food.delivery.helper.AppMessages;
import food.delivery.model.Location;
import food.delivery.repository.LocationRepository;
import food.delivery.security.SecurityUtil;
import food.delivery.service.LocationService;
import food.delivery.service.mapper.interfaces.LocationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;


    @Override
    public ResponseEntity<?> addLocation(LocationDto locationDto) {
        Long userId = SecurityUtil.getUserDto().getId();
        List<Location> locations = locationRepository.findAllByUserId(userId);

        if (locations.size() == 10) {
            return ResponseEntity.badRequest().body("Location size 10");
        }

        Location location = locationMapper.toEntity(locationDto);
        location.setUserId(userId);
        location.setActive(true);

        locationRepository.save(location);

        return ResponseEntity.accepted().body(AppMessages.SAVED);
    }


    @Override
    public ResponseEntity<?> getById(Long id) {
        Optional<Location> optional = locationRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        LocationDto locationDto = locationMapper.toDto(optional.get());

        return ResponseEntity.ok().body(locationDto);
    }


    @Override
    public ResponseEntity<?> getAllLocationByUserId() {
        Long userId = SecurityUtil.getUserDto().getId();
        List<LocationDto> locations = locationRepository.findAllByUserIdAndActive(userId, true)
                .stream().map(locationMapper::toDto).toList();

       if (locations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content");
        }

        return ResponseEntity.ok().body(locations);
    }


    @Override
    public ResponseEntity<?> currentLocation(Long id) {
        Long userId = SecurityUtil.getUserDto().getId();
        Optional<Location> optional = locationRepository.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content");
        }
        LocationDto location = locationMapper.toDto(optional.get());

        return ResponseEntity.accepted().body(location);
    }


    @Override
    public ResponseEntity<?> updateLocation(LocationDto locationDto) {
        Long id = locationDto.getId();
        if (id == null) {
            return ResponseEntity.badRequest().body("id not found");
        }
        Optional<Location> optional = locationRepository.findById(id);
        Location location = optional.get();

        location.setAddress(locationDto.getAddress());
        location.setLatitude(locationDto.getLatitude());
        location.setLongitude(locationDto.getLongitude());
        locationRepository.save(location);
        locationDto = locationMapper.toDto(location);

        return ResponseEntity.accepted().body(locationDto);
    }


    @Override
    public ResponseEntity<?> deleteById(Long id) {
        Location location = locationRepository.findById(id).get();
        location.setActive(false);
//        locationRepository.deleteById(id);

        locationRepository.save(location);

        return ResponseEntity.ok().body("Deleted");
    }
}
