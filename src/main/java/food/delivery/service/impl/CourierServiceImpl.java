package food.delivery.service.impl;

import food.delivery.model.Order;
import food.delivery.service.CourierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierServiceImpl implements CourierService {



    @Override
    public ResponseEntity<?> addOrderCourierList(Order order) {
        return null;
    }

    @Override
    public ResponseEntity<?> couriersNotBusy() {
        return null;
    }

    @Override
    public ResponseEntity<?> addOrderCourierOrderList(Order order) {
        return null;
    }

    @Override
    public ResponseEntity<?> courierOrdersList() {
        return null;
    }

    @Override
    public ResponseEntity<?> orderDelivered(Order order) {
        return null;
    }

    @Override
    public ResponseEntity<?> joinCouriersListNotBusy() {
        return null;
    }
}
