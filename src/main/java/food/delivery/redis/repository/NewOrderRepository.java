package food.delivery.redis.repository;

import food.delivery.redis.model.NewOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewOrderRepository extends CrudRepository<NewOrder, Long> {

    List<NewOrder> findAllByFilialId(Integer filialId);

}
