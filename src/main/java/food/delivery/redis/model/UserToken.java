package food.delivery.redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "userToken", timeToLive = 60 * 60 * 24 * 1000)
public class UserToken {

    @Id
    private Long id;
    private String token;

}