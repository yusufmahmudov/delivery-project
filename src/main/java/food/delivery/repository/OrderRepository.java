package food.delivery.repository;

import food.delivery.dto.OrderDto;
import food.delivery.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

//    @Query("SELECT COUNT(o) FROM Order o WHERE o.createdAt >= :startDate AND o.createdAt < :endDate")
//    List<Order> findByCreatedAtBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);


    List<Order> findByCourierIdAndCreatedAtBetween(Integer courierId, LocalDateTime start, LocalDateTime end);


    void deleteAllByPaidFalseAndCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<Order> findAllByUserId(Long userId);
}
