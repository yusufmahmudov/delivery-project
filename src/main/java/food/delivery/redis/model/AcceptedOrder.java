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
@RedisHash(value = "accepted_order", timeToLive = 60 * 60 * 24)
public class AcceptedOrder {
    /** Qabul qilingan buyurtmalar */

    @Id
    private Long orderId;
    private Integer filialId;
    private OrderDto orderDto;
    private Integer tableNumber;

}
