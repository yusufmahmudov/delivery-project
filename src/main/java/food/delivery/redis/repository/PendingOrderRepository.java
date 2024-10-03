package food.delivery.redis.repository;

import food.delivery.redis.model.PendingOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PendingOrderRepository extends CrudRepository<PendingOrder, Long> {

    List<PendingOrder> findAllByFilialId(Integer filialId);

}
