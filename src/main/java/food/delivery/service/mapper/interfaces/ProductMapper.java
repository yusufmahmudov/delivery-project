package food.delivery.service.mapper.interfaces;

import food.delivery.dto.ProductDto;
import food.delivery.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductDto productDto);
    ProductDto toDto(Product product);
}
