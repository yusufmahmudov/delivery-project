package food.delivery.service.mapper;


import food.delivery.dto.ProductDto;
import food.delivery.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public static ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .description(product.getDescription())
                .active(product.getActive())
                .extra(product.getExtra())
                .extraList(product.getExtraList())
                .bonusList(product.getBonusList())
                .imagePath(product.getImagePath())
                .categoryDto(CategoryMapper.toDtoWithoutProduct(product.getCategory()))
                .build();
    }

    public static Product toEntity(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .discount(productDto.getDiscount())
                .description(productDto.getDescription())
                .active(productDto.getActive())
                .extra(productDto.getExtra())
                .extraList(productDto.getExtraList())
                .bonusList(productDto.getBonusList())
                .imagePath(productDto.getImagePath())
                .category(CategoryMapper.toEntityWithoutProduct(productDto.getCategoryDto()))
                .build();
    }


    public static ProductDto toDtoWithoutCategory(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .description(product.getDescription())
                .active(product.getActive())
                .extra(product.getExtra())
                .extraList(product.getExtraList())
                .bonusList(product.getBonusList())
                .imagePath(product.getImagePath())
                .build();
    }

    public static Product toEntityWithoutCategory(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .discount(productDto.getDiscount())
                .description(productDto.getDescription())
                .active(productDto.getActive())
                .extra(productDto.getExtra())
                .extraList(productDto.getExtraList())
                .bonusList(productDto.getBonusList())
                .imagePath(productDto.getImagePath())
                .build();
    }

}
