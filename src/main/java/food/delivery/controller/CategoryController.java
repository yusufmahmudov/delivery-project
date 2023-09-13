package food.delivery.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import food.delivery.dto.CategoryDto;
import food.delivery.dto.response.ResponseDto;
import food.delivery.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Tag(name = "category", description = "Mahsulot kategoriyasi boshqaruv apilari")
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Yangi kategoriya qo'shish", tags = {"category", "post"})
    @PostMapping("/add")
    public ResponseDto<String> addCategory(
            @Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }


    @Operation(summary = "Hamma kategoriyalarni chiqaradi", tags = {"category", "get"})
    @GetMapping("/all")
    public ResponseDto<List<CategoryDto>> allCategory() {
        return categoryService.allCategory();
    }


    @Operation(summary = "Hamma kategoriya va kategoriyaga tegishli mahsulotlarni chiqarish",
            tags = {"category", "product", "get"})
    @GetMapping("/all-category-and-product")
    public ResponseDto<List<CategoryDto>> allCategoryWithProducts() {
        return categoryService.allCategoryWithProducts();
    }


    @Operation(summary = "Hamma kategoriya va kategoriyaga tegishli 'active' fieldi true mahsulotlarni chiqarish",
            tags = {"category", "product", "get"})
    @GetMapping("/all-category-and-product-active")
    public ResponseDto<List<CategoryDto>> getAllCategoriesWithActiveProducts(
            @RequestParam Boolean active) {
        return categoryService.getAllCategoriesWithActiveProducts(active);
    }


    @Operation(summary = "'id' bo'yicha kategoriyani chiqarish",
            tags = {"category", "get"})
    @GetMapping("/by-id")
    public ResponseDto<CategoryDto> getById(
            @RequestParam @Min(1) Integer id) {
        return categoryService.getById(id);
    }


    @Operation(summary = "'id' bo'yicha kategoriya va unga tegishli mahsulotlarni chiqarish",
            tags = {"category", "product", "get"})
    @GetMapping("/with-products/by-id")
    public ResponseDto<CategoryDto> getByIdWithProducts(
            @RequestParam @Min(1) Integer id) {
        return categoryService.getByIdWithProducts(id);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "'id' bo'yicha kategoriyani o'chirib tashlash",
            tags = {"category", "delete"})
    @DeleteMapping("/by-id")
    public ResponseDto<String> deleteById(
            @RequestParam @Min(1) Integer id) {
        return categoryService.deleteById(id);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "'id' bo'yicha kategoriyani tahrirlash",
            tags = {"category", "put"})
    @PutMapping("/update")
    public ResponseDto<CategoryDto> updateCategory(
            @Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(categoryDto);
    }

}