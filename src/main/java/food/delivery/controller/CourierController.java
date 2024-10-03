package food.delivery.controller;

import food.delivery.model.Order;
import food.delivery.service.CourierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/courier")
@RestController
@RequiredArgsConstructor
@Tag(name = "courier", description = "Courier apilari bilan ishlash")
public class CourierController {

    private final CourierService courierService;


    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Courierlar listiga yangi order qo'shish", tags = {"order", "courier", "post"})
    @PostMapping("/add-order")
    public ResponseEntity<?> addOrderCourierList(@RequestBody Order order) {
        return courierService.addOrderCourierList(order);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Band bo'lmagan courierlar listini chiqarish", tags = {"courier", "get"})
    @GetMapping("/list-couriers")
    public ResponseEntity<?> couriersNotBusy() {
        return courierService.couriersNotBusy();
    }


    @PreAuthorize("hasRole('ROLE_COURIER')") // <-- hasRole('ROLE_ADMIN')
    @Operation(summary = "Orderni courierning buyurtmalari listisga qo'shish", tags = {"courier", "post"})
    @PostMapping("/add-order-courier-list")
    public ResponseEntity<?> addOrderCourierOrderList(
            @RequestBody Order order) {
        return courierService.addOrderCourierOrderList(order);
    }


    @PreAuthorize("hasRole('ROLE_COURIER')")
    @Operation(summary = "Courier shaxsiy order listini chiqarish", tags = {"order", "courier", "get"})
    @GetMapping("/list-orders")
    public ResponseEntity<?> courierOrdersList() {
        return courierService.courierOrdersList();
    }


    @PreAuthorize("hasRole('ROLE_COURIER')")
    @Operation(summary = "Yetqazib berilgan orderni saqlash", tags = {"order", "courier", "patch"})
    @PatchMapping("/order-delivered")
    public ResponseEntity<?> orderDelivered(@RequestBody Order order) {
        return courierService.orderDelivered(order);
    }


    @PreAuthorize("hasRole('ROLE_COURIER')")
    @Operation(summary = "Band bo'lmagan courierlar listiga qo'shilish", tags = {"courier", "post"})
    @PostMapping("/join-courier")
    public ResponseEntity<?> joinCouriersListNotBusy() {
        return courierService.joinCouriersListNotBusy();
    }
}
