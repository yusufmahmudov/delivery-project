package food.delivery.repository;

import food.delivery.model.DeliveryPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryPriceRepository extends JpaRepository<DeliveryPrice, Integer> {

//    @Query(value = "SELECT d, max(d.id) FROM DeliveryPrice d")
//    Integer activeDeliveryPrice();

    DeliveryPrice findTopByOrderByIdDesc();
}
