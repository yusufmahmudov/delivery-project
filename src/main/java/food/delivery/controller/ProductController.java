package food.delivery.controller;

import food.delivery.helper.AppMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import food.delivery.dto.ProductDto;
import food.delivery.dto.response.ResponseDto;
import food.delivery.service.ProductService;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "product", description = "Mahsulot ma'lumotlari bilan ishlash uchun apilar")
@Validated
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Rasmsiz yangi mahsulot qo'shish uchun",
            tags = {"product", "post"})
    @PostMapping("/add")
    public ResponseDto<String> add(
            @Valid @RequestBody ProductDto productDto) {
        return productService.add(productDto);
    }


//    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Rasm va product fieldlarini qo'shish orqali yangi mahsulot qo'shish",
            tags = {"product", "post"})
    @PostMapping("/add-product")
    public ResponseDto<ProductDto> addProduct(
            @RequestParam("image") MultipartFile multipartFile,

            @RequestParam @NotNull(message = AppMessages.EMPTY_FIELD)
            @Size(min = 3, max = 255, message = "length min 3, max 255") String name,

            @RequestParam @NotNull(message = AppMessages.EMPTY_FIELD)
            @DecimalMin(value = "0.0", message = "min value 0.0") Double price,

            @RequestParam @NotNull(message = AppMessages.EMPTY_FIELD)
            @DecimalMin(value = "0.0", message = "min value 0.0")
            @DecimalMax(value = "100.0", message = "max value 100.0") Double discount,

            @RequestParam @NotNull(message = AppMessages.EMPTY_FIELD)
            @Size(min = 1, max = 255, message = "length min 1, max 255") String description,

            @RequestParam @NotNull(message = AppMessages.EMPTY_FIELD) Boolean extra,

            @RequestParam @NotNull(message = AppMessages.EMPTY_FIELD) Boolean active,

            @RequestParam String extraList,

            @RequestParam String bonusList,

            @RequestParam @NotNull(message = AppMessages.EMPTY_FIELD)
            @Min(1) Integer categoryId
            ) {
        return productService.addProduct(multipartFile, name, price,
                discount, description, active, extra, extraList, bonusList, categoryId);
    }


    @Operation(summary = "Barcha mahsulotlarni chiqarish",
            tags = {"product", "get"})
    @GetMapping("/all")
    public ResponseDto<List<ProductDto>> allProducts() {
        return productService.allProducts();
    }


    @Operation(summary = "Barcha active mahsulotlarni chiqarish",
            tags = {"product", "get"})
    @GetMapping("/all-active")
    public ResponseDto<List<ProductDto>> allByActive(
            @RequestParam Boolean active) {
        return productService.allByActive(active);
    }


    @Operation(summary = "Mahsulotni 'id'si bo'yicha chiqarish",
            tags = {"product", "get"})
    @GetMapping("/by-id")
    public ResponseDto<ProductDto> getById(
            @RequestParam @Min(1) Integer id) {
        return productService.getById(id);
    }


    @GetMapping("/get-all-extra")
    @Operation(summary = "Hamma qo'shimcha yryish mumkin bo'lgan mahsulotlar",
            tags = {"product", "get"})
    public ResponseEntity<?> getAllExtraProduct() {
        return productService.getAllExtraProduct();
    }


    @GetMapping("/get-all-extra-by-id")
    @Operation(summary = "Id bo'yicha -- Hamma qo'shimcha yryish mumkin bo'lgan mahsulotlar",
            tags = {"product", "get"})
    public ResponseEntity<?> getAllExtraProductById(
            @RequestParam @Min(1) Integer id) {
        return productService.getAllExtraProductById(id);
    }


    @GetMapping("/get-all-bonus-by-id")
    @Operation(summary = "ID bo'yicha -- Hamma bonusga beriladigan mahsulotlar",
            tags = {"product", "get"})
    public ResponseEntity<?> getAllBonusProductById(
            @RequestParam @Min(1) Integer id) {
        return productService.getAllBonusProductById(id);
    }


    @PatchMapping("/set-bonus")
    @Operation(summary = "Mahsulotning bonuslarini o'zgartirish",
            tags = {"product", "patch"})
    public ResponseEntity<?> setBonusList(
            @RequestParam @Min(1) Integer id,
            @RequestParam String bonusList) {
        return productService.setBonusList(id, bonusList);
    }


    @PatchMapping("/set-extra")
    @Operation(summary = "Mahsulotni qo'shib yeyish mumkin bo'lgan mahsulotlarni o'zgartirish",
            tags = {"product", "patch"})
    public ResponseEntity<?> setExtraList(
            @RequestParam @Min(1) Integer id,
            @RequestParam String extraList) {
        return productService.setExtraList(id, extraList);
    }


//    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Mahsulotni 'id'si bo'yicha o'chirib tashlash",
            tags = {"product", "delete"})
    @DeleteMapping("/by-id")
    public ResponseDto<String> deleteById(
            @RequestParam @Min(1) Integer id) {
        return productService.deleteById(id);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Mahsulotni tahrirlash",
            tags = {"product", "put"})
    @PutMapping("/update")
    public ResponseDto<ProductDto> updateProduct(
            @RequestBody ProductDto productDto) {
        return productService.updateProduct(productDto);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Mahsulot active bo'lsa passiv, passiv bo'lsa active holatga keltirish",
            tags = {"product", "patch"})
    @PatchMapping("/set-active/by-id")
    public ResponseDto<String> setIsActive(
            @RequestParam @Min(1) Integer id,
            @RequestParam Boolean active
    ) {
        return productService.setIsActive(id, active);
    }


    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Mahsulotga chegirma foizni o'rnatish. Mahsulot 'id'si va chegirma foizi",
            tags = {"product", "patch"})
    @PatchMapping("/set-discount")
    public ResponseDto<String> setDiscount(
            @RequestParam @Min(1) Integer id,
            @DecimalMin(value = "0.0") @DecimalMax(value = "100.0")
            @RequestParam Double discount
    ) {
        return productService.setDiscount(id, discount);
    }


//    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    @Operation(summary = "Mahsulot uchun biriktirilgan rasmni almashtirish",
            tags = {"product", "image", "patch"})
    @PatchMapping("/upload-image")
    public ResponseEntity<?> updateImage(
            @RequestParam("image") MultipartFile multipartFile,
            @RequestParam @Min(1) Integer id
    ) {
        return productService.updateImage(multipartFile, id);
    }

}
