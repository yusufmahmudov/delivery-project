package food.delivery.service;


import food.delivery.dto.CategoryDto;
import food.delivery.dto.response.ResponseDto;

import java.util.List;

public interface CategoryService {

    ResponseDto<String> addCategory(CategoryDto categoryDto);

    ResponseDto<List<CategoryDto>> allCategory();

    ResponseDto<List<CategoryDto>> allCategoryWithProducts();

    ResponseDto<List<CategoryDto>> getAllCategoriesWithActiveProducts(Boolean active);

    ResponseDto<CategoryDto> getById(Integer id);

    ResponseDto<CategoryDto> getByIdWithProducts(Integer id);

    ResponseDto<String> deleteById(Integer id);

    ResponseDto<CategoryDto> updateCategory(CategoryDto categoryDto);
}
