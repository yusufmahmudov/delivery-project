package food.delivery.service.impl;

import food.delivery.component.WebSocketUtil;
import food.delivery.dto.*;
import food.delivery.helper.AppMessages;
import food.delivery.model.Filial;
import food.delivery.model.Location;
import food.delivery.model.Order;
import food.delivery.redis.model.*;
import food.delivery.redis.repository.*;
import food.delivery.repository.FilialRepository;
import food.delivery.repository.LocationRepository;
import food.delivery.repository.OrderRepository;
import food.delivery.security.SecurityUtil;
import food.delivery.service.FilialService;
import food.delivery.service.OrderProductService;
import food.delivery.service.OrderService;
import food.delivery.service.mapper.interfaces.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    public static Integer ORDER_NUMBER = 1;

    private final OrderMapper orderMapper;
    private final FilialService filialService;
    private final OrderRepository orderRepository;
    private final LocationRepository locationRepository;
    private final OrderProductService orderProductService;

    private final FilialRepository filialRepository;
    private final NewOrderRepository newOrderRepository;
    private final AcceptedOrderRepository acceptedOrderRepository;
    private final PendingOrderRepository pendingOrderRepository;
    private final ReadyOrderRepository readyOrderRepository;
    private final CanceledOrderRepository canceledOrderRepository;
    private final WebSocketUtil webSocketUtil;

    @Value("${delivery.radius}")
    private Double deliveryRadius;


    @Override
    public ResponseEntity<?> orderProcessing(OrderDto orderDto) {
        try {
            Long userId = SecurityUtil.getUserDto().getId();
            orderDto.setUserId(userId);
            FilialDto filial;

            if (orderDto.getOrderType().equals(AppMessages.DELIVERY)) {
                ResponseEntity<?> response =
                        locationDetermination(orderDto.getLocationId());
                if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
                }
                Map<Double, FilialDto> map = (Map<Double, FilialDto>) response.getBody();
                assert map != null;
                filial = map.values().stream().findFirst().get();
                orderDto.setFilialId(filial.getId());
            } else {
                filial = (FilialDto) filialService.getById(orderDto.getFilialId()).getBody();
            }
            String orderNumber = generateOrderNumber();
            orderDto.setOrderNumber(orderNumber);
//            orderDto.setPaid(false);
            orderDto.setStatus(AppMessages.FORMALIZED);

            Order order = orderMapper.toEntity(orderDto);
            order.setCreatedAt(LocalDateTime.now());
            orderRepository.save(order);
            orderDto.setId(order.getId());

            Integer quantity = (Integer) orderProductService.saveOrderProducts(
                    orderDto.getOrderProducts(), orderDto.getId()).getBody();

            orderDto.setQuantity(quantity);
            order.setQuantity(quantity);
            orderRepository.save(order);

//            Integer admin = filial.getAdminId();
//            if (admin == null) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
//            }
            // TODO: WEBSOCKET
            NewOrder newOrder = new NewOrder();
            newOrder.setOrderId(orderDto.getId());
//            newOrder.setFilialId(filial.getId());
            newOrder.setOrderDto(orderDto);

//            newOrderRepository.save(newOrder);

            webSocketUtil.sendNewOrder(newOrder);

            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> orderByEmployee(OrderDto orderDto) {
        try {
            Integer employeeId = SecurityUtil.getEmployeeDto().getId().intValue();
            orderDto.setEmployeeId(employeeId);
            // TODO: FILIAL ID???

            String orderNumber = generateOrderNumber();
            orderDto.setOrderNumber(orderNumber);
            orderDto.setPaid(false);
            orderDto.setStatus(AppMessages.FORMALIZED);
            orderDto.setOrderType(AppMessages.BY_EMPLOYEE);

            Order order = orderMapper.toEntity(orderDto);
            order.setCreatedAt(LocalDateTime.now());
            order.setOrderTime(LocalDateTime.now());
            orderRepository.save(order);
            orderDto.setId(order.getId());

            orderProductService.saveOrderProducts(
                    orderDto.getOrderProducts(), orderDto.getId());

            AcceptedOrder acceptedOrder = new AcceptedOrder();
            acceptedOrder.setOrderId(orderDto.getId());
            acceptedOrder.setTableNumber(orderDto.getTableNumber());
            acceptedOrder.setFilialId(orderDto.getFilialId());
            acceptedOrder.setOrderDto(orderDto);

            acceptedOrderRepository.save(acceptedOrder);

            webSocketUtil.sendAcceptedOrder(acceptedOrder);

            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> addExtraToTheOrder(OrderDto orderDto) {
        try {
            Integer employeeId = SecurityUtil.getEmployeeDto().getId().intValue();
            if (!employeeId.equals(orderDto.getEmployeeId())) {
                return ResponseEntity.internalServerError().body("Bu sizning buyurtmangiz emas!");
            }
            orderProductService.saveOrderProducts(
                    orderDto.getOrderProducts(), orderDto.getId());

            AcceptedOrder acceptedOrder = acceptedOrderRepository.findById(orderDto.getId()).get();
            acceptedOrder.setTableNumber(orderDto.getTableNumber());
            acceptedOrder.setOrderDto(orderDto);

            acceptedOrderRepository.save(acceptedOrder);

            webSocketUtil.sendAcceptedOrder(acceptedOrder);

            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /*
        private Long userId; X
        private Integer courierId; X
        private Integer employeeId; A
        private Integer cashierId; A
        private Double servicingPrice; A
        private Integer filialId; A
        private String orderNumber; A
        private Integer tableNumber; A -1
        private Boolean paid; A
        private String paymentType; A
        private String orderType; A
        private Long locationId; X
        private Double deliveryPrice; X
        private Double totalPrice; A
        private Integer quantity; X
        private String comment; A
        private String notice; X
        private String status; A
        private LocalDateTime orderTime; A
        private LocalDateTime createdAt; X
        private LocalDateTime orderCompletedTime; A
        private List<OrderedProduct> orderProducts; A
    */

    @Override
    public ResponseEntity<?> orderByCashier(OrderDto orderDto) {
        try {
            Integer employeeId = SecurityUtil.getEmployeeDto().getId().intValue();
            orderDto.setEmployeeId(employeeId);
            orderDto.setCashierId(employeeId);
            // TODO: FILIAL ID???

            String orderNumber = generateOrderNumber();
            orderDto.setOrderNumber(orderNumber);
            orderDto.setPaid(false);
            orderDto.setStatus(AppMessages.FORMALIZED);
            orderDto.setOrderType(AppMessages.BY_EMPLOYEE);
            orderDto.setServicingPrice(0.0);

            Order order = orderMapper.toEntity(orderDto);
            order.setCreatedAt(LocalDateTime.now());
            order.setOrderTime(LocalDateTime.now());
            order.setOrderCompletedTime(LocalDateTime.now());
            orderDto = orderMapper.toDto(order);
            order = orderMapper.toEntity(orderDto);
            orderRepository.save(order);
            orderDto.setId(order.getId());

            orderDto = orderProductService.saveOrderProductsByAllData(
                    orderDto.getOrderProducts(), orderDto).getData();

            AcceptedOrder acceptedOrder = new AcceptedOrder();
            acceptedOrder.setOrderId(orderDto.getId());
            acceptedOrder.setTableNumber(orderDto.getTableNumber());
            acceptedOrder.setFilialId(orderDto.getFilialId());
            acceptedOrder.setOrderDto(orderDto);

            acceptedOrderRepository.save(acceptedOrder);

            webSocketUtil.sendAcceptedOrder(acceptedOrder);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderDto);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> orderCompletion(OrderDto orderDto, Integer filialId) {
        try {
            Integer employeeId = SecurityUtil.getEmployeeDto().getId().intValue();
            orderDto.setEmployeeId(orderDto.getEmployeeId());
            orderDto.setCashierId(employeeId);
            // TODO: FILIAL ID???

            Filial filial = filialRepository.findById(orderDto.getFilialId()).get();

            orderDto.setStatus(AppMessages.COMPLETION);
            orderDto.setOrderType(AppMessages.BY_EMPLOYEE);

            orderDto = orderProductService.saveOrderProductsByAllData(
                    orderDto.getOrderProducts(), orderDto).getData();
            orderDto.setServicingPrice(orderDto.getTotalPrice()/filial.getServicing());

            Order order = orderMapper.toEntity(orderDto);
            order.setOrderCompletedTime(LocalDateTime.now());
            orderDto = orderMapper.toDto(order);
            order = orderMapper.toEntity(orderDto);
            orderRepository.save(order);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderDto);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    private String generateOrderNumber() {
        LocalTime currentTime = LocalTime.now();
        int minutes = currentTime.getMinute();
        int seconds = currentTime.getSecond();

        StringBuilder formattedTime = new StringBuilder("#" + minutes);
        if (ORDER_NUMBER < 10) {
            formattedTime.append("00");
            formattedTime.append(ORDER_NUMBER);
            formattedTime.append(seconds);
        } else if (ORDER_NUMBER < 100) {
            formattedTime.append("0");
            formattedTime.append(ORDER_NUMBER);
            formattedTime.append(seconds);
        } else {
            formattedTime.append(ORDER_NUMBER + seconds);
        }

        return formattedTime.toString();
    }


    @Override
    public ResponseEntity<?> locationDetermination(Long locationId) {

        Optional<Location> optional = locationRepository.findById(locationId);

        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
        Location location = optional.get();

        Double lat = location.getLatitude();
        Double lon = location.getLongitude();

        SortedMap<Double, FilialDto> filialDtoMap = filialService.checkTheDistanceForOrder(lat, lon);

        for (Double dis : filialDtoMap.keySet()){
            if (dis > deliveryRadius) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
            }
            break;
        }
        Map<Double, FilialDto> map = new HashMap<>();
        Double key = filialDtoMap.firstKey();
        FilialDto filial = filialDtoMap.get(key);
        map.put(key, filial);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(map);
    }


    @Override
    public ResponseEntity<?> orderAcceptance(OrderDto orderDto, Integer filialId) {
        return null;
    }


    @Override
    public ResponseEntity<?> getAllOrderAcceptance(Integer filialId) {
        return null;
    }


    @Override
    public ResponseEntity<?> orderCancellation(OrderDto orderDto, Integer filialId) {
        return null;
    }


    @Override
    public ResponseEntity<?> getAllOrderCancellation(Integer filialId) {
        return null;
    }


    @Override
    public ResponseEntity<?> orderPending(OrderDto orderDto, Integer filialId) {
        try {
            orderDto.setStatus(AppMessages.PENDING);

            Order order = orderMapper.toEntity(orderDto);
            orderRepository.save(order);

            PendingOrder pendingOrder = new PendingOrder();
            pendingOrder.setOrderId(orderDto.getId());
            pendingOrder.setTableNumber(orderDto.getTableNumber());
            pendingOrder.setFilialId(orderDto.getFilialId());
            pendingOrder.setOrderDto(orderDto);

            // TODO avvalgi redis modeldan o'chirish
            pendingOrderRepository.save(pendingOrder);

            webSocketUtil.sendPendingOrder(pendingOrder);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderDto);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> getAllOrderPending(Integer filialId) {
        return null;
    }


    @Override
    public ResponseEntity<?> orderReady(OrderDto orderDto, Integer filialId) {
        try {
            orderDto.setStatus(AppMessages.READY);

            Order order = orderMapper.toEntity(orderDto);
            orderRepository.save(order);

            ReadyOrder readyOrder = new ReadyOrder();
            readyOrder.setOrderId(orderDto.getId());
            readyOrder.setTableNumber(orderDto.getTableNumber());
            readyOrder.setFilialId(orderDto.getFilialId());
            readyOrder.setOrderDto(orderDto);

            readyOrderRepository.save(readyOrder);

            webSocketUtil.sendReadyOrder(readyOrder);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderDto);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> getAllOrderReady(Integer filialId, String orderType) {
        return null;
    }


    @Override
    public ResponseEntity<?> getAllOrderReady() {
        return null;
    }


    @Override
    public ResponseEntity<?> addOrderCourierBasket(OrderDto orderDto) {
        return null;
    }


    @Override
    public ResponseEntity<?> getAllOrderCourierBasket() {
        return null;
    }


    @Override
    public ResponseEntity<?> orderDelivered(OrderDto orderDto) {
        return null;
    }


    @Override
    public ResponseEntity<?> orderTaken(OrderDto orderDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllOrder(String status) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.minusDays(1);

        List<OrderDto> orders = orderRepository.findByCreatedAtBetween(start, now)
                .stream().map(orderMapper::toDto).toList();

        List<OrderDto> resOrders;

        if (status.isEmpty()) {
            resOrders = orders.stream()
                    .filter(o -> o.getStatus().equals(AppMessages.DELIVERY) ||
                            o.getStatus().equals(AppMessages.ACCEPTED)).toList();
        } else {
            resOrders = orders.stream()
                    .filter(o -> o.getStatus().equals(status)).toList();
        }

        return ResponseEntity.ok().body(resOrders);
    }



    @Override
    public ResponseEntity<?> getAllOrderCancellationByUser() {
        Long userId = SecurityUtil.getUserDto().getId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("403");
        }

        List<CanceledOrder> canceledOrders =
                (List<CanceledOrder>) canceledOrderRepository.findAll();

        List<OrderDto> orders = canceledOrders.stream()
                .map(CanceledOrder::getOrderDto)
                .filter(orderDto -> Objects.equals(orderDto.getUserId(), userId)).toList();

        return ResponseEntity.ok().body(orders);
    }


    @Override
    public ResponseEntity<?> getAllOrderProcess() {
        Long userId = SecurityUtil.getUserDto().getId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("403");
        }

        List<OrderDto> orders = orderRepository.findAllByUserId(userId)
                .stream().map(orderMapper::toDto)
                .filter(o -> !o.getStatus().equals(AppMessages.DELIVERY) &&
                        !o.getStatus().equals(AppMessages.ACCEPTED)).toList();

        return ResponseEntity.ok().body(orders);
    }


    @Override
    public ResponseEntity<?> getOrder(Long orderId) {
        Optional<Order> optional = orderRepository.findById(orderId);
        OrderDto orderDto = orderMapper.toDto(optional.get());

        return ResponseEntity.ok().body(orderDto);
    }


    @Override
    public ResponseEntity<?> getAllOrderHistory() {
        Long userId = SecurityUtil.getUserDto().getId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("403");
        }

        List<OrderDto> orders = orderRepository.findAllByUserId(userId)
                .stream().map(orderMapper::toDto)
                .filter(o -> o.getStatus().equals(AppMessages.DELIVERY) ||
                        o.getStatus().equals(AppMessages.ACCEPTED)).toList();

        return ResponseEntity.ok().body(orders);
    }


}
