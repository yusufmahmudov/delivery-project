package food.delivery.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import food.delivery.service.OrderProductService;

@RestController
@RequestMapping("/order-product")
@RequiredArgsConstructor
@Tag(name = "OrderProduct", description = "Tashqi apilar uchun emas")
public class OrderProductController{

    private final OrderProductService orderProductService;

//    @GetMapping("/price-calculation")
//    public ResponseDto<List<OrderedProductDto>> priceCalculation(@RequestBody ResponseDto<List<OrderedProductDto>> productsDetails) {
//        return orderProductService.priceCalculation(productsDetails);
//    }
}
