package food.delivery.redis.repository;

import food.delivery.redis.model.ReadyOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadyOrderRepository extends CrudRepository<ReadyOrder, Long> {

    List<ReadyOrder> findAllByFilialId(Integer filialId);

    List<ReadyOrder> findAllByFilialIdAndStatus(Integer filialId, String status);

    List<ReadyOrder> findAllOrders();

}
