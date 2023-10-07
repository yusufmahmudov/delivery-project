package food.delivery.repository;

import food.delivery.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByActive(boolean active);

    List<Product> findByIdIn(List<Integer> productIds);

    List<Product> findAllByExtra(boolean extra);
}
