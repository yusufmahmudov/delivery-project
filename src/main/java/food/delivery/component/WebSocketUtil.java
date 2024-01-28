package food.delivery.component;

import food.delivery.redis.model.AcceptedOrder;
import food.delivery.redis.model.NewOrder;
import food.delivery.redis.repository.NewOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WebSocketUtil {

    private final SimpMessagingTemplate messagingTemplate;
    private final NewOrderRepository newOrderRepository;


    @Scheduled(fixedDelay = 5000L)
    public void sendNewOrderList() {
        List<NewOrder> orders = (List<NewOrder>) newOrderRepository.findAll();
        messagingTemplate.convertAndSend("/topic/new-orders", orders);
    }


    public void sendNewOrder(NewOrder order) {
        /* websocket orqali yangi buyurtmalani yuborish */
        messagingTemplate.convertAndSend("/topic/new-order", order);
    }


    public void sendAcceptedOrder(AcceptedOrder acceptedOrder) {
        /* websocket orqali qabul qilingan buyurtmalarni yuborish */
        messagingTemplate.convertAndSend("topic/accepted-order", acceptedOrder);
    }

}
