package food.delivery.service;


import food.delivery.dto.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;


public interface OrderService {

    /** Buyurtmani rasmiylashtirish, adminga yuborish */
    ResponseEntity<?> orderProcessing(OrderDto orderDto);


    /** Xodim afitsant tomonidan buyurtmani rasmiylashtirish */
    ResponseEntity<?> orderByEmployee(OrderDto orderDto);


    /** Buyurtmaga biror narsa qo'shish */
    ResponseEntity<?> addExtraToTheOrder(OrderDto orderDto);


    /** Kassir tomonidan buyurtmani rasmiylashtirish */
    ResponseEntity<?> orderByCashier(OrderDto orderDto);


    /** Userga eng yaqin filialni aniqlash */
    ResponseEntity<?> locationDetermination(Long locationId);


    /** Qabul qilingan buyurtmani qo'shish */
    ResponseEntity<?> orderAcceptance(OrderDto orderDto, Integer filialId);


    /** Barcha qabul qilingan buyurtmalar */
    ResponseEntity<?> getAllOrderAcceptance(Integer filialId);


    /** Bekor qilingan buyurtmani qo'shish */
    ResponseEntity<?> orderCancellation(OrderDto orderDto, Integer filialId);


    /** Barcha bekor qilingan buyurtmalar */
    ResponseEntity<?> getAllOrderCancellation(Integer filialId);


    /** Tayyorlanishni boshlagan buyurtmani qo'shish */
    ResponseEntity<?> orderPending(OrderDto orderDto, Integer filialId);


    /** Barcha tayyorlanayotgan buyurtmalar */
    ResponseEntity<?> getAllOrderPending(Integer filialId);


    /** Tayyor bo'lgan buyurtmani qo'shish */
    ResponseEntity<?> orderReady(OrderDto orderDto, Integer filialId);


    /** Barcha tayyor bo'lgan buyurtmalar */
    ResponseEntity<?> getAllOrderReady(Integer filialId, String orderType);


    /** Barcha tayyor bo'lgan buyurtmalar */
    ResponseEntity<?> getAllOrderReady();


    /** Buyurtmani courierning savatchasiga qo'shish */
    ResponseEntity<?> addOrderCourierBasket(OrderDto orderDto);


    /** Courierning savatchasidagi barcha buyurtmalar */
    ResponseEntity<?> getAllOrderCourierBasket();


    /** Yetqazib berilganga qo'shish */
    ResponseEntity<?> orderDelivered(OrderDto orderDto);


    /** Olib ketilganlarga qo'shish */
    ResponseEntity<?> orderTaken(OrderDto orderDto);


    /** Barcha buyurtmalar */
    ResponseEntity<?> getAllOrder(String status);




    /** Barcha bekor qilingan buyurtmalar */
    ResponseEntity<?> getAllOrderCancellationByUser();


    /** Barcha jarayonda bo'lgan buyurtmalar */
    ResponseEntity<?> getAllOrderProcess();


    /** Order ma'lumotlari */
    ResponseEntity<?> getOrder(Long orderId);


    /** Barcha  */
    ResponseEntity<?> getAllOrderHistory();

}
