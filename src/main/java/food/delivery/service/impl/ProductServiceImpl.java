package food.delivery.service.impl;

import food.delivery.dto.CategoryDto;
import food.delivery.dto.ProductDto;
import food.delivery.dto.response.ResponseDto;
import food.delivery.dto.response.ValidatorDto;
import food.delivery.dto.template.ImageDto;
import food.delivery.helper.AppCode;
import food.delivery.helper.AppMessages;
import food.delivery.helper.StringHelper;
import food.delivery.model.Category;
import food.delivery.model.Product;
import food.delivery.repository.CategoryRepository;
import food.delivery.repository.OrderRepository;
import food.delivery.repository.ProductRepository;
import food.delivery.service.ImageService;
import food.delivery.service.ProductService;
import food.delivery.service.ValidatorService;
import food.delivery.service.mapper.CategoryMapper;
import food.delivery.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.PersistenceException;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static food.delivery.helper.AppCode.ERROR;
import static food.delivery.helper.AppCode.VALIDATOR_ERROR;
import static food.delivery.helper.AppMessages.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ValidatorService validatorService;
    private final ImageService imageService;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;


    @Override
    public ResponseDto<String> add(ProductDto productDto) {
        try {
            Optional<Category> optional = categoryRepository.findById(productDto.getCategoryDto().getId());
            if (optional.isEmpty()) {
                return ResponseDto.<String>builder()
                        .message("Category Id " + NOT_FOUND)
                        .code(AppCode.NOT_FOUND)
                        .success(false)
                        .build();
            }

            if (productDto.getActive() == null) productDto.setActive(false);
            if (productDto.getDiscount() == null) productDto.setDiscount(0.0);
            if (productDto.getPrice() == null) productDto.setPrice(0.0);

            Product product = ProductMapper.toEntity(productDto);
            productRepository.save(product);

            return ResponseDto.<String>builder()
                    .code(AppCode.OK)
                    .data("ID: " + product.getId())
                    .message(SAVED)
                    .success(true)
                    .build();
        }catch (DataAccessException | PersistenceException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseDto.<String>builder()
                    .code(ERROR)
                    .success(false)
                    .message(e.getMessage())
                    .build();
        }
    }


    @Override
    public ResponseDto<ProductDto> addProduct(
            MultipartFile multipartFile,
            String name,
            Double price,
            Double discount,
            String description,
            Boolean active,
            Boolean extra,
            String extraList,
            String bonusList,
            Integer categoryId) {
        try {
            CategoryDto categoryDto = CategoryMapper.toDtoWithoutProduct(
                    categoryRepository.findById(categoryId).get());
            ProductDto productDto = new ProductDto();
            productDto.setName(name);
            productDto.setPrice(price);
            productDto.setDiscount(discount);
            productDto.setDescription(description);
            productDto.setCategoryDto(categoryDto);
            productDto.setExtra(extra);
            productDto.setActive(active);

            List<Integer> list1 = StringHelper.parseString(extraList);
            productDto.setExtraList(list1);

            List<Integer> list2 = StringHelper.parseString(bonusList);
            productDto.setBonusList(list2);

            List<ValidatorDto> errors = new ArrayList<>();
            if (multipartFile == null || multipartFile.getOriginalFilename() == null) {
                errors.add(new ValidatorDto("image", EMPTY_FIELD));
                return ResponseDto.<ProductDto>builder()
                        .code(VALIDATOR_ERROR)
                        .errors(errors)
                        .message(VALIDATOR_MESSAGE)
                        .success(false)
                        .build();
            }

            if (productDto.getExtra() == null) productDto.setExtra(false);
            if (productDto.getActive() == null) productDto.setActive(false);
            if (productDto.getDiscount() == null) productDto.setDiscount(0.0);
            if (productDto.getPrice() == null) productDto.setPrice(0.0);

            Product product = ProductMapper.toEntity(productDto);
            productRepository.save(product);

            ImageDto image = (ImageDto) imageService
                    .addImage(multipartFile, "product image").getBody();

            assert image != null;
            product.setImagePath(image.getImagePath());
            product.setImageId(image.getId());
//            image.setProductId(product.getId());
//            imageRepository.save(image);

            productRepository.save(product);

            return ResponseDto.<ProductDto>builder()
                    .code(AppCode.OK)
                    .data(ProductMapper.toDto(product))
                    .message(SAVED)
                    .success(true)
                    .build();
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseDto.<ProductDto>builder()
                    .code(ERROR)
                    .success(false)
                    .message(e.getMessage())
                    .build();
        }
    }


    @Override
    public ResponseDto<List<ProductDto>> allProducts() {
        try {
            List<ProductDto> productDtoList = productRepository.findAll()
                    .stream().map(ProductMapper::toDto).toList();

            return ResponseDto.<List<ProductDto>>builder()
                    .data(productDtoList)
                    .message(productDtoList.isEmpty() ? NOT_FOUND : AppMessages.OK)
                    .success(true)
                    .build();
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseDto.<List<ProductDto>>builder()
                    .code(ERROR)
                    .success(false)
                    .message(e.getMessage())
                    .build();
        }
    }


    @Override
    public ResponseDto<List<ProductDto>> allByActive(Boolean active) {
        try {
            List<ProductDto> productDtoList = productRepository.findAllByActive(active)
                    .stream().map(ProductMapper::toDto).toList();

            return ResponseDto.<List<ProductDto>>builder()
                    .data(productDtoList)
                    .message(productDtoList.isEmpty() ? NOT_FOUND : AppMessages.OK)
                    .success(true)
                    .code(AppCode.OK)
                    .build();
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseDto.<List<ProductDto>>builder()
                    .code(ERROR)
                    .success(false)
                    .message(e.getMessage())
                    .build();
        }
    }


    @Override
    public ResponseDto<ProductDto> getById(Integer id) {
        try {
            Optional<Product> optional = productRepository.findById(id);
            if (optional.isEmpty()) {
                return ResponseDto.<ProductDto>builder()
                        .success(false)
                        .message(AppMessages.NOT_FOUND)
                        .code(AppCode.NOT_FOUND)
                        .build();
            }
            ProductDto productDto = ProductMapper.toDto(optional.get());
            return ResponseDto.<ProductDto>builder()
                    .data(productDto)
                    .message(AppMessages.OK)
                    .code(AppCode.OK)
                    .success(true)
                    .build();
        }catch (DataAccessException | IllegalArgumentException e){
            log.error(e.getMessage());
            return ResponseDto.<ProductDto>builder()
                    .message(e.getMessage())
                    .code(ERROR)
                    .success(false)
                    .build();
        }
    }


    @Override
    public ResponseEntity<?> weekBestProducts() {
        // TODO

        return null;
    }


    @Override
    public ResponseEntity<?> getAllExtraProduct() {
        List<ProductDto> products = productRepository.findAllByExtra(true)
                .stream().map(ProductMapper::toDto).toList();

        return ResponseEntity.ok().body(products);
    }


    @Override
    public ResponseEntity<?> getAllExtraProductById(Integer id) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Integer> list = optional.get().getExtraList();
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ProductDto> products = productRepository.findByIdIn(list)
                .stream().map(ProductMapper::toDto).toList();;

        return ResponseEntity.ok().body(products);
    }


    @Override
    public ResponseEntity<?> getAllBonusProductById(Integer id) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Integer> bonusIds = optional.get().getBonusList();
        if (bonusIds.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ProductDto> products = productRepository.findByIdIn(bonusIds)
                .stream().map(ProductMapper::toDto).toList();

        return ResponseEntity.ok().body(products);
    }


    @Override
    public ResponseEntity<?> setBonusList(Integer id, String bonusList) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Integer> bonusIds = StringHelper.parseString(bonusList);
        if (bonusIds.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product product = optional.get();
        product.setBonusList(bonusIds);
        productRepository.save(product);

        return ResponseEntity.accepted().build();
    }


    @Override
    public ResponseEntity<?> setExtraList(Integer id, String extraList) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Integer> extraIds = StringHelper.parseString(extraList);
        if (extraIds.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product product = optional.get();
        product.setBonusList(extraIds);
        productRepository.save(product);

        return ResponseEntity.accepted().build();
    }


    @Override
    public ResponseDto<String> deleteById(Integer id) {
        try {
            productRepository.deleteById(id);

            return ResponseDto.<String>builder()
                    .message(AppMessages.OK)
                    .code(AppCode.OK)
                    .success(true)
                    .build();
        }catch (DataAccessException | IllegalArgumentException e){
            log.error(e.getMessage());
            return ResponseDto.<String>builder()
                    .message(e.getMessage())
                    .code(ERROR)
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<ProductDto> updateProduct(ProductDto productDto) {
        try {
            if (productDto.getId() == null) {
                return ResponseDto.<ProductDto>builder()
                        .success(false)
                        .message("Id " + EMPTY_FIELD)
                        .code(ERROR)
                        .build();
            }
            Optional<Product> optional = productRepository.findById(productDto.getId());
            if (optional.isEmpty()){
                return ResponseDto.<ProductDto>builder()
                        .success(true)
                        .message(AppMessages.NOT_FOUND)
                        .code(AppCode.NOT_FOUND)
                        .build();
            }
            Optional<Category> optional1 = categoryRepository.findById(productDto.getCategoryDto().getId());
            if (optional1.isEmpty()) {
                return ResponseDto.<ProductDto>builder()
                        .message("Category Id " + NOT_FOUND)
                        .code(AppCode.NOT_FOUND)
                        .success(false)
                        .build();
            }

            Product product1 = optional.get();
            List<ValidatorDto> errors = validatorService.validateProduct(productDto);
            if (!errors.isEmpty()){
                return ResponseDto.<ProductDto>builder()
                        .code(VALIDATOR_ERROR)
                        .errors(errors)
                        .message(VALIDATOR_MESSAGE)
                        .success(false)
                        .build();
            }
            Product product2 = ProductMapper.toEntity(productDto);

            product1.setName(product2.getName() == null ? product1.getName() : product2.getName());
            product1.setPrice(product2.getPrice() == null ? product1.getPrice() : product2.getPrice());
            product1.setDiscount(product2.getDiscount() == null ? product1.getDiscount() : product2.getDiscount());
            product1.setDescription(product2.getDescription() == null ? product1.getDescription() : product2.getDescription());
            product1.setActive(product2.getActive() == null ? product1.getActive() : product2.getActive());
            product1.setExtra(product2.getExtra() == null ? product1.getExtra() : product2.getExtra());
            product1.setCategory(product2.getCategory() == null ? product1.getCategory() : product2.getCategory());

            productRepository.save(product1);
            productDto = ProductMapper.toDto(product1);
            return ResponseDto.<ProductDto>builder()
                    .data(productDto)
                    .code(AppCode.OK)
                    .message(AppMessages.OK)
                    .success(true)
                    .build();
        }catch (NullPointerException | IllegalArgumentException | DataAccessException | NoSuchElementException e){
            log.error(e.getMessage());
            return ResponseDto.<ProductDto>builder()
                    .message(e.getMessage())
                    .code(ERROR)
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<String> setIsActive(Integer id, Boolean active) {
        try {
            Optional<Product> optional = productRepository.findById(id);
            if (optional.isEmpty()) {
                return ResponseDto.<String>builder()
                        .success(true)
                        .message(NOT_FOUND)
                        .code(AppCode.NOT_FOUND)
                        .build();
            }
            Product product = optional.get();
            product.setActive(active);
            productRepository.save(product);

            return ResponseDto.<String>builder()
                    .data(product.getActive() ? "True" : "False")
                    .message(AppMessages.OK)
                    .code(AppCode.OK)
                    .success(true)
                    .build();
        }catch (DataAccessException | NoSuchElementException e){
            log.error(e.getMessage());
            return ResponseDto.<String>builder()
                    .message(e.getMessage())
                    .code(ERROR)
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<String> setDiscount(Integer id, Double discount) {
        try {
            Optional<Product> optional = productRepository.findById(id);
            if (optional.isEmpty()) {
                return ResponseDto.<String>builder()
                        .success(true)
                        .message(NOT_FOUND)
                        .code(AppCode.NOT_FOUND)
                        .build();
            }

            Product product = optional.get();
            product.setDiscount(discount);
            productRepository.save(product);

            return ResponseDto.<String>builder()
                    .data(product.getName() + " ga " + discount + " foizli chegirma o'rnatildi")
                    .message(AppMessages.OK)
                    .code(AppCode.OK)
                    .success(true)
                    .build();
        }catch (DataAccessException | NoSuchElementException e){
            log.error(e.getMessage());
            return ResponseDto.<String>builder()
                    .message(e.getMessage())
                    .code(ERROR)
                    .success(false)
                    .build();
        }
    }


    @Override
    public ResponseEntity<?> updateImage(MultipartFile multipartFile, Integer id) {
        if (multipartFile == null) {
            return ResponseEntity.badRequest().body("image not found");
        }
        Optional<Product> optional = productRepository.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product not found");
        }

        Product product = optional.get();
        Long imageId = product.getImageId();

        ImageDto image = (ImageDto) imageService
                .addImage(multipartFile, "product image").getBody();

        assert image != null;
        product.setImagePath(image.getImagePath());
        product.setImageId(image.getId());
        productRepository.save(product);

        boolean check = (boolean) imageService.deleteImage(imageId).getBody();
        if (!check) {
            return ResponseEntity.internalServerError().body("Old image not deleted");
        }

//        image.setProductId(product.getId());
//        imageRepository.save(image);

        return ResponseEntity.ok().body(product.getImagePath());
    }


    @Override
    public ResponseEntity<?> productSearch(String name) {

        String finalName = name.toLowerCase();

        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products.stream()
                .filter(p -> p.getName().toLowerCase().contains(finalName))
                .map(ProductMapper::toDto).toList();

        if (productDtos.isEmpty()) {
            return ResponseEntity.badRequest().body("Not found");
        }

        return ResponseEntity.ok().body(productDtos);
    }


}
