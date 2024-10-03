package food.delivery.service.mapper;


import food.delivery.dto.CategoryDto;
import food.delivery.model.Category;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public static CategoryDto toDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .products(category.getProducts()
                        .stream().map(ProductMapper::toDtoWithoutCategory)
                        .collect(Collectors.toList()))
                .build();
    }

    public static Category toEntity(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .products(categoryDto.getProducts()
                        .stream().map(ProductMapper::toEntityWithoutCategory)
                        .collect(Collectors.toList()))
                .build();
    }


    public static CategoryDto toDtoWithoutProduct(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category toEntityWithoutProduct(CategoryDto categoryDto){
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }


}
