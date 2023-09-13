package food.delivery.redis.model;

import food.delivery.dto.OrderDto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "canceled_order", timeToLive = 60 * 60 * 24)
public class CanceledOrder {
    /** Qaytarilgan buyurtmalar */

    @Id
    private Long orderId;
    private Integer filialId;
    private OrderDto orderDto;
}
