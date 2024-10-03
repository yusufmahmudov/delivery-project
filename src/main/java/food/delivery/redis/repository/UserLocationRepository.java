package food.delivery.redis.repository;

import food.delivery.redis.model.UserLocation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLocationRepository extends CrudRepository<UserLocation, Long> {

}
