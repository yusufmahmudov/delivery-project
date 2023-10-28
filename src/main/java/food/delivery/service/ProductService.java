package food.delivery.service;

import food.delivery.dto.ProductDto;
import food.delivery.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    ResponseEntity<?> add(ProductDto productDto);

    ResponseEntity<?> addProduct(
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

    ResponseEntity<?> allProducts(Integer limit, Integer offset);

    ResponseEntity<?> allByActive(Boolean active, Integer limit, Integer offset);

    ResponseEntity<?> getById(Integer id);

    ResponseEntity<?> weekBestProducts();

    ResponseEntity<?> getAllExtraProduct(Integer limit, Integer offset);

    ResponseEntity<?> getAllExtraProductById(Integer id, Integer limit, Integer offset);

    ResponseEntity<?> getAllBonusProductById(Integer id, Integer limit, Integer offset);

    ResponseEntity<?> setBonusList(Integer id, String bonusList);

    ResponseEntity<?> setExtraList(Integer id, String extraList);

    ResponseEntity<?> deleteById(Integer id);

    ResponseEntity<?> updateProduct(ProductDto productDto);

    ResponseEntity<?> setIsActive(Integer id, Boolean active);

    ResponseEntity<?> setDiscount(Integer id, Double discount);

    ResponseEntity<?> updateImage(MultipartFile multipartFile, Integer id);

    ResponseEntity<?> productSearch(String name);
}
