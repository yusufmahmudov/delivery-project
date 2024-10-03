package food.delivery.service.mapper.interfaces;

import food.delivery.dto.OrderedProductDto;
import food.delivery.model.OrderedProduct;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderedProductsMapper {

    OrderedProduct toEntity(OrderedProductDto orderedProductsDto);
    OrderedProductDto toDto(OrderedProduct orderedProducts);

}
