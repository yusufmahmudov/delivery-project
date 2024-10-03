package food.delivery.service;


import food.delivery.dto.OrderDto;
import food.delivery.dto.OrderedProductDto;
import food.delivery.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderProductService {

    ResponseEntity<?> saveOrderProducts(List<OrderedProductDto> orderedProducts, Long orderId);


    /**  */
    ResponseDto<OrderDto> saveOrderProductsByAllData(List<OrderedProductDto> orderedProducts, OrderDto orderDto);

}
