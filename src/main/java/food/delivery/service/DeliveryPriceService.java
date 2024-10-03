package food.delivery.service;


import food.delivery.dto.DeliveryPriceDto;
import food.delivery.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface DeliveryPriceService {

    ResponseEntity<?> addNewPrice(DeliveryPriceDto deliveryPriceDto);

    ResponseEntity<?> getDeliveryPrice();

    ResponseEntity<?> getPrice();
}
