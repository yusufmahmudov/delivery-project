package food.delivery.redis.repository;

import food.delivery.redis.model.CourierBasket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourierBasketRepository extends CrudRepository<CourierBasket, Long> {

    List<CourierBasket> findAllByCourierId(Integer courierId);

}
