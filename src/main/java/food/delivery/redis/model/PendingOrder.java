package food.delivery.redis.model;

import food.delivery.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "pending_order", timeToLive = 60 * 60 * 24)
public class PendingOrder {
    /** Tayyorlanayotgan buyurtmalar */

    @Id
    private Long orderId;
    private Integer filialId;
    private OrderDto orderDto;
}
