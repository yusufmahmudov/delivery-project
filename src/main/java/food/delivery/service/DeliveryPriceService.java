package food.delivery.service;


import food.delivery.dto.DeliveryPriceDto;
import food.delivery.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface DeliveryPriceService {

    ResponseDto<String> addNewPrice(DeliveryPriceDto deliveryPriceDto);

    ResponseDto<DeliveryPriceDto> getDeliveryPrice();

    ResponseEntity<?> getPrice();
}
