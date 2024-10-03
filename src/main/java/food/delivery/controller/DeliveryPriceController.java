package food.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import food.delivery.dto.DeliveryPriceDto;
import food.delivery.service.DeliveryPriceService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery-price")
@Tag(name = "deliveryPrice", description = "Yetkazib berish narxi boshqaruvi class apilari")
@Validated
@PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
public class DeliveryPriceController {

    private final DeliveryPriceService deliveryPriceService;

    @Operation(summary = "Yangi narx qo'shish",
            tags = {"deliveryPrice", "post"})
    @PostMapping("/add")
    public ResponseEntity<?> addNewPrice(
            @Valid @RequestBody DeliveryPriceDto deliveryPriceDto) {
        return deliveryPriceService.addNewPrice(deliveryPriceDto);
    }


    @Operation(summary = "Yetkazib berish narxlarini qaytaradi",
            tags = {"deliveryPrice", "get"})
    @GetMapping("/get")
    public ResponseEntity<?> getDeliveryPrice() {
        return deliveryPriceService.getDeliveryPrice();
    }


    @Operation(summary = "Yetkazib berish narxini qaytaradi 'ASOSIY'",
            tags = {"deliveryPrice", "get"})
    @GetMapping("/price")
    public ResponseEntity<?> getPrice() {
        return deliveryPriceService.getPrice();
    }

}