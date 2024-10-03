package food.delivery.redis.repository;

import food.delivery.redis.model.AcceptedOrder;
import food.delivery.redis.model.NewOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcceptedOrderRepository extends CrudRepository<AcceptedOrder, Long> {

    List<AcceptedOrder> findAllByFilialId(Integer filialId);

}
