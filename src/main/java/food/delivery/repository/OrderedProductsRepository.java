package food.delivery.repository;

import food.delivery.model.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedProductsRepository extends JpaRepository<OrderedProduct, Long> {
    List<OrderedProduct> findAllByOrderId(Long id);


}
