package food.delivery.redis.repository;

import food.delivery.redis.model.CanceledOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanceledOrderRepository extends CrudRepository<CanceledOrder, Long> {

}
