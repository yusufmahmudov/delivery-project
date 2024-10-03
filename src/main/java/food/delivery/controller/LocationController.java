package food.delivery.controller;

import food.delivery.dto.LocationDto;
import food.delivery.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
@Tag(name = "location", description = "Location ma'lumotlari bilan ishlash uchun apilar")
@Validated
public class LocationController {

    private final LocationService locationService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Yangi Location qo'shish",
            tags = {"location", "post"})
    @PostMapping("/add")
    public ResponseEntity<?> addLocation(
            @RequestBody LocationDto locationDto) {
        return locationService.addLocation(locationDto);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Operation(summary = "locationId va userId orqali location chiqairsh",
            tags = {"location", "get"})
    @GetMapping("/get-by-id")
    public ResponseEntity<?> getByLocation(
            @RequestParam @Min(1) Long id) {
        return locationService.getById(id);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Operation(summary = "userId orqali locationlarni chiqairsh",
            tags = {"location", "get"})
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllLocationByUserId() {
        return locationService.getAllLocationByUserId();
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Locationni asosiy location qilib tayinlash",
            tags = {"location", "patch"})
    @PatchMapping("/current-location")
    public ResponseEntity<?> currentLocation(
            @RequestParam @Min(1) Long id) {
        return locationService.currentLocation(id);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Locationni yangilash",
            tags = {"location", "put"})
    @PutMapping("/update")
    public ResponseEntity<?> updateLocation(
            @RequestBody @Valid LocationDto locationDto) {
        return locationService.updateLocation(locationDto);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "id orqali locationni o'chirish",
            tags = {"location", "delete"})
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteLocation(
            @RequestParam @Min(1) Long id) {
        return locationService.deleteById(id);
    }


}
