package food.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import food.delivery.dto.OrderDto;
import food.delivery.service.OrderService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@Tag(name = "order", description = "Buyurtma ma'lumotlari bilan ishlovchi apilar")
@Validated
public class OrderController {

    private final OrderService orderService;


    @Operation(summary = "Buyurtmani rasmiylashtiruvchi method",
            tags = {"order", "post"})
    @PostMapping("/order-processing")
    public ResponseEntity<?> orderProcessing(
            @Valid @RequestBody OrderDto orderDto) {
        return orderService.orderProcessing(orderDto);
    }


    @Operation(summary = "Xodim Buyurtmani rasmiylashtiruvchi method",
            tags = {"order", "post"})
    @PostMapping("/order-by-employee")
    public ResponseEntity<?> orderByEmployee(
            @RequestBody OrderDto orderDto) {
        return orderService.orderByEmployee(orderDto);
    }


    @Operation(summary = "Buyurtmaga o'zgatirish kiritish. Faqat asitsant buyurtmalari uchun",
            tags = {"order", "patch"})
    @PatchMapping("/add-extra-to-the-order")
    public ResponseEntity<?> addExtraToTheOrder(
            @RequestBody OrderDto orderDto) {
        return orderService.addExtraToTheOrder(orderDto);
    }


    @Operation(summary = "Kassir buyurtmani rasmiylashtirishi. Chek olish qismi",
            tags = {"order", "post"})
    @PostMapping("/order-by-cashier")
    public ResponseEntity<?> orderByCashier(
            @RequestBody OrderDto orderDto) {
        return orderService.orderByCashier(orderDto);
    }


    @Operation(summary = "Buyurtmani rasmiylashtiruvchi method",
            tags = {"order", "get"})
    @PostMapping("/order-acceptance")
    public ResponseEntity<?> orderAcceptance(
            @RequestBody OrderDto orderDto,
            @RequestParam Integer filialId) {
        return orderService.orderAcceptance(orderDto, filialId);
    }


    @Operation(summary = "Barcha rasmiylashtirilgan buyurtmalar",
            tags = {"order", "get"})
    @GetMapping("/get-all-order-acceptance")
    public ResponseEntity<?> getAllOrderAcceptance(
            @RequestParam Integer filialId) {
        return orderService.getAllOrderAcceptance(filialId);
    }


    @Operation(summary = "Buyurtmani rasmiylashtiruvchi method",
            tags = {"order", "post"})
    @PostMapping("/order-cancellation")
    public ResponseEntity<?> orderCancellation(
            @RequestBody OrderDto orderDto,
            @RequestParam Integer filialId) {
        return orderService.orderCancellation(orderDto, filialId);
    }


    @Operation(summary = "Barcha bekor qilingan buyurtmalar",
            tags = {"order", "get"})
    @GetMapping("/get-all-order-cancellation")
    public ResponseEntity<?> getAllOrderCancellation(
            @RequestParam Integer filialId) {
        return orderService.getAllOrderCancellation(filialId);
    }


    @Operation(summary = "Buyurtmani rasmiylashtiruvchi method",
            tags = {"order", "post"})
    @PostMapping("/order-pending")
    public ResponseEntity<?> orderPending(
            @RequestBody OrderDto orderDto,
            @RequestParam Integer filialId) {
        return orderService.orderPending(orderDto, filialId);
    }


    @Operation(summary = "Barcha tayyorlanayotgan buyurtmalar",
            tags = {"order", "get"})
    @GetMapping("/get-all-order-pending")
    public ResponseEntity<?> getAllOrderPending(
            @RequestParam Integer filialId) {
        return orderService.getAllOrderPending(filialId);
    }


    @Operation(summary = "Buyurtmani rasmiylashtiruvchi method",
            tags = {"order", "post"})
    @PostMapping("/order-ready")
    public ResponseEntity<?> orderReady(
            @RequestBody OrderDto orderDto,
            @RequestParam Integer filialId) {
        return orderService.orderReady(orderDto, filialId);
    }


    @Operation(summary = "Buyurtmani rasmiylashtiruvchi method",
            tags = {"order", "get"})
    @GetMapping("/get-all-order-ready-by-filial")
    public ResponseEntity<?> getAllOrderReady(
            @RequestParam Integer filialId,
            @RequestParam String orderType) {
        return orderService.getAllOrderReady(filialId, orderType);
    }


    @Operation(summary = "Buyurtmani rasmiylashtiruvchi method",
            tags = {"order", "get"})
    @GetMapping("/get-all-order-ready")
    public ResponseEntity<?> getAllOrderReady() {
        return orderService.getAllOrderReady();
    }


    @Operation(summary = "Buyurtmani rasmiylashtiruvchi method",
            tags = {"order", "get"})
    @PostMapping("/add-order-courier-basket")
    public ResponseEntity<?> addOrderCourierBasket(
            @RequestBody OrderDto orderDto) {
        return orderService.addOrderCourierBasket(orderDto);
    }


    @Operation(summary = "Buyurtmani rasmiylashtiruvchi method",
            tags = {"order", "get"})
    @GetMapping("/get-all-order-courier-basket")
    public ResponseEntity<?> getAllOrderCourierBasket() {
        return orderService.getAllOrderCourierBasket();
    }


    @Operation(summary = "Buyurtmani rasmiylashtiruvchi method",
            tags = {"order", "post"})
    @PostMapping("/order-delivered")
    public ResponseEntity<?> orderDelivered(
            @RequestBody OrderDto orderDto) {
        return orderService.orderDelivered(orderDto);
    }


    @Operation(summary = "Buyurtmani rasmiylashtiruvchi method",
            tags = {"order", "post"})
    @PostMapping("/order-taken")
    public ResponseEntity<?> orderTaken(
            @RequestBody OrderDto orderDto) {
        return orderService.orderTaken(orderDto);
    }


    @Operation(summary = "Buyurtmani rasmiylashtiruvchi method",
            tags = {"order", "get"})
    @GetMapping("/get-all-order")
    public ResponseEntity<?> getAllOrder(
            @RequestParam String status) {
        return orderService.getAllOrder(status);
    }


    @Operation(summary = "Buyurtmani rasmiylashtiruvchi method",
            tags = {"order", "get"})
    @GetMapping("/get-all-order-cancellation-by-user")
    public ResponseEntity<?> getAllOrderCancellationByUser() {
        return orderService.getAllOrderCancellationByUser();
    }


    @Operation(summary = "Buyurtmani rasmiylashtiruvchi method",
            tags = {"order", "get"})
    @GetMapping("/get-all-order-process")
    public ResponseEntity<?> getAllOrderProcess() {
        return orderService.getAllOrderProcess();
    }


    @Operation(summary = "Buyurtmani rasmiylashtiruvchi method",
            tags = {"order", "get"})
    @GetMapping("/get-order")
    public ResponseEntity<?> getOrder(
            @RequestParam Long orderId) {
        return orderService.getOrder(orderId);
    }


    @Operation(summary = "Buyurtmani rasmiylashtiruvchi method",
            tags = {"order", "get"})
    @GetMapping("/get-all-order-history")
    public ResponseEntity<?> getAllOrderHistory() {
        return orderService.getAllOrderHistory();
    }

}
