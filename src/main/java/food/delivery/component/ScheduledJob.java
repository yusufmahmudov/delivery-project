package food.delivery.component;

import food.delivery.repository.OrderRepository;
import food.delivery.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScheduledJob {

    private final OrderRepository orderRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @Scheduled(cron = "${order.delete.cron}", zone = "Asia/Karachi")
    @Transactional
    public void setupOrderNumber() {
        OrderServiceImpl.ORDER_NUMBER = 1;
    }


    /** order ma'lumotlarini paid true bo'lmaganlarini o'chirish.
     * order.delete.cron application.properties da berilgan.
     * har kuni tungi 02:00 da ishga tushadi */
    @Scheduled(cron = "${order.delete.cron}", zone = "Asia/Karachi")
    public void clearingOrders() {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusDays(1);
        orderRepository.deleteAllByPaidFalseAndCreatedAtBetween(start, end);
    }


//    @Scheduled(cron = "0 */5 * ? * *")
//    @Transactional
//    public void resetOrderTable() {
//        entityManager.createNativeQuery("TRUNCATE TABLE TEMP_ORDERS").executeUpdate();
//        entityManager.createNativeQuery("ALTER TABLE TEMP_ORDERS ALTER COLUMN id RESTART WITH 1").executeUpdate();
//    }


//    @Scheduled(cron = "0 */5 * ? * *")
//    @Transactional
//    public void resetImageTable() {
//        entityManager.createNativeQuery("TRUNCATE TABLE IMAGE").executeUpdate();
//        entityManager.createNativeQuery("ALTER TABLE IMAGE ALTER COLUMN id RESTART WITH 1").executeUpdate();
//    }

}
