package food.delivery.service;

import food.delivery.dto.ProductDto;
import food.delivery.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ResponseDto<String> add(ProductDto productDto);

    ResponseDto<ProductDto> addProduct(
            MultipartFile multipartFile,
            String name,
            Double price,
            Double discount,
            String description,
            Boolean active,
            Boolean extra,
            String extraList,
            String bounsList,
            Integer categoryId);

    ResponseDto<List<ProductDto>> allProducts();

    ResponseDto<List<ProductDto>> allByActive(Boolean active);

    ResponseDto<ProductDto> getById(Integer id);

    ResponseEntity<?> weekBestProducts();

    ResponseEntity<?> getAllExtraProduct();

    ResponseEntity<?> getAllExtraProductById(Integer id);

    ResponseEntity<?> getAllBonusProductById(Integer id);

    ResponseEntity<?> setBonusList(Integer id, String bonusList);

    ResponseEntity<?> setExtraList(Integer id, String extraList);

    ResponseDto<String> deleteById(Integer id);

    ResponseDto<ProductDto> updateProduct(ProductDto productDto);

    ResponseDto<String> setIsActive(Integer id, Boolean active);

    ResponseDto<String> setDiscount(Integer id, Double discount);

    ResponseEntity<?> updateImage(MultipartFile multipartFile, Integer id);

    ResponseEntity<?> productSearch(String name);
}
