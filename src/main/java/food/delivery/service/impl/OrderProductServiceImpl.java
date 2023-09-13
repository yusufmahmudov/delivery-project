package food.delivery.service.impl;

import food.delivery.dto.OrderedProductDto;
import food.delivery.dto.response.ResponseDto;
import food.delivery.model.OrderedProduct;
import food.delivery.repository.OrderedProductsRepository;
import food.delivery.repository.ProductRepository;
import food.delivery.service.OrderProductService;
import food.delivery.service.mapper.interfaces.OrderedProductsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProductServiceImpl implements OrderProductService {

    private final OrderedProductsMapper orderedProductsMapper;
    private final OrderedProductsRepository orderedProductsRepository;


    @Override
    public ResponseEntity<?> saveOrderProducts(
            List<OrderedProductDto> orderedProducts, Long orderId) {

        Integer count = 0;
        List<OrderedProduct> list = orderedProducts.stream()
                .map(orderedProductsMapper::toEntity).toList();

        for (OrderedProduct o : list) {
            o.setOrderId(1L);
            count += o.getAmount();
        }

        orderedProductsRepository.saveAll(list);

        return ResponseEntity.accepted().body(count);
    }


}
