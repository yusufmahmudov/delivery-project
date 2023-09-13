package food.delivery.redis.model;

import food.delivery.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "userLocation", timeToLive = 60 * 60 * 24 * 1000)
public class UserLocation {

    @Id
    private Long id;
    private LocationDto location;

}