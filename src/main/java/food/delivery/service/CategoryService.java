package food.delivery.service;


import food.delivery.dto.CategoryDto;
import org.springframework.http.ResponseEntity;

public interface CategoryService {

    ResponseEntity<?> addCategory(CategoryDto categoryDto);

    ResponseEntity<?> allCategory(Integer limit, Integer offset);

    ResponseEntity<?> allCategoryWithProducts(Integer limit, Integer offset);

    ResponseEntity<?> getAllCategoriesWithActiveProducts(Boolean active, Integer limit, Integer offset);

    ResponseEntity<?> getById(Integer id);

    ResponseEntity<?> getByIdWithProducts(Integer id);

    ResponseEntity<?> deleteById(Integer id);

    ResponseEntity<?> updateCategory(CategoryDto categoryDto);
}
