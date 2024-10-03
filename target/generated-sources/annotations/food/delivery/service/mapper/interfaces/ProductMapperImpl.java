package food.delivery.service.mapper.interfaces;

import food.delivery.dto.ProductDto;
import food.delivery.dto.ProductDto.ProductDtoBuilder;
import food.delivery.model.Product;
import food.delivery.model.Product.ProductBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-03T23:21:44+0500",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toEntity(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        ProductBuilder product = Product.builder();

        product.id( productDto.getId() );
        product.name( productDto.getName() );
        product.price( productDto.getPrice() );
        product.discount( productDto.getDiscount() );
        product.active( productDto.getActive() );
        product.imagePath( productDto.getImagePath() );
        product.imageId( productDto.getImageId() );
        product.extra( productDto.getExtra() );
        product.description( productDto.getDescription() );
        List<Integer> list = productDto.getExtraList();
        if ( list != null ) {
            product.extraList( new ArrayList<Integer>( list ) );
        }
        List<Integer> list1 = productDto.getBonusList();
        if ( list1 != null ) {
            product.bonusList( new ArrayList<Integer>( list1 ) );
        }

        return product.build();
    }

    @Override
    public ProductDto toDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDtoBuilder productDto = ProductDto.builder();

        productDto.id( product.getId() );
        productDto.name( product.getName() );
        productDto.price( product.getPrice() );
        productDto.discount( product.getDiscount() );
        productDto.active( product.getActive() );
        productDto.imagePath( product.getImagePath() );
        productDto.imageId( product.getImageId() );
        productDto.extra( product.getExtra() );
        productDto.description( product.getDescription() );
        List<Integer> list = product.getExtraList();
        if ( list != null ) {
            productDto.extraList( new ArrayList<Integer>( list ) );
        }
        List<Integer> list1 = product.getBonusList();
        if ( list1 != null ) {
            productDto.bonusList( new ArrayList<Integer>( list1 ) );
        }

        return productDto.build();
    }
}
