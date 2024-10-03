package food.delivery.service;

import food.delivery.model.Order;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

public interface CourierService {

    @Operation(description = "Buyurtma qabul qilinganda orderni courierlar uchun tanlashga chiqarish")
    ResponseEntity<?> addOrderCourierList(Order order);

    @Operation(description = "Band bo'lmagan courierlarni adminga ko'rsatish")
    ResponseEntity<?> couriersNotBusy();

    @Operation(description = "Orderni courierning order listiga qo'shish")
    ResponseEntity<?> addOrderCourierOrderList(Order order);

    @Operation(description = "Courier qabul qilgan orderlar ro'yxati")
    ResponseEntity<?> courierOrdersList();

    @Operation(description = "Yetqazib berilgan order")
    ResponseEntity<?> orderDelivered(Order order);

    @Operation(description = "Courier o'zini band bo'lmagan courierlar ro'yxatiga qo'shish")
    ResponseEntity<?> joinCouriersListNotBusy();

}