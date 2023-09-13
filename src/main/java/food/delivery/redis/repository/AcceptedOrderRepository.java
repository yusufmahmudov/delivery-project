package food.delivery.redis.repository;

import food.delivery.redis.model.AcceptedOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcceptedOrderRepository extends CrudRepository<AcceptedOrder, Long> {

}
