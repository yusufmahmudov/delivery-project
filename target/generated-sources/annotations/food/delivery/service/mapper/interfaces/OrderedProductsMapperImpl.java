package food.delivery.service.mapper.interfaces;

import food.delivery.dto.OrderedProductDto;
import food.delivery.dto.OrderedProductDto.OrderedProductDtoBuilder;
import food.delivery.model.OrderedProduct;
import food.delivery.model.OrderedProduct.OrderedProductBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-03T22:34:36+0500",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class OrderedProductsMapperImpl implements OrderedProductsMapper {

    @Override
    public OrderedProduct toEntity(OrderedProductDto orderedProductsDto) {
        if ( orderedProductsDto == null ) {
            return null;
        }

        OrderedProductBuilder orderedProduct = OrderedProduct.builder();

        orderedProduct.orderId( orderedProductsDto.getOrderId() );
        orderedProduct.productId( orderedProductsDto.getProductId() );
        orderedProduct.price( orderedProductsDto.getPrice() );
        orderedProduct.discount( orderedProductsDto.getDiscount() );
        orderedProduct.discountPrice( orderedProductsDto.getDiscountPrice() );
        orderedProduct.totalPrice( orderedProductsDto.getTotalPrice() );
        orderedProduct.amount( orderedProductsDto.getAmount() );

        return orderedProduct.build();
    }

    @Override
    public OrderedProductDto toDto(OrderedProduct orderedProducts) {
        if ( orderedProducts == null ) {
            return null;
        }

        OrderedProductDtoBuilder orderedProductDto = OrderedProductDto.builder();

        orderedProductDto.orderId( orderedProducts.getOrderId() );
        orderedProductDto.productId( orderedProducts.getProductId() );
        orderedProductDto.price( orderedProducts.getPrice() );
        orderedProductDto.discount( orderedProducts.getDiscount() );
        orderedProductDto.discountPrice( orderedProducts.getDiscountPrice() );
        orderedProductDto.totalPrice( orderedProducts.getTotalPrice() );
        orderedProductDto.amount( orderedProducts.getAmount() );

        return orderedProductDto.build();
    }
}
