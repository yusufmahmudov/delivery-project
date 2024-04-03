package food.delivery.redis.repository;

import food.delivery.redis.model.CanceledOrder;
import food.delivery.redis.model.NewOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CanceledOrderRepository extends CrudRepository<CanceledOrder, Long> {

    List<CanceledOrder> findAllByFilialId(Integer filialId);

}
