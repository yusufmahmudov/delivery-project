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
@RedisHash(value = "new_order", timeToLive = 60 * 60 * 24)
public class NewOrder {
    /** Yangi buyurtmalar */

    @Id
    private Long orderId;
    private Integer filialId;
    private OrderDto orderDto;
}
