package food.delivery.service.impl;

import food.delivery.dto.CategoryDto;
import food.delivery.dto.ProductDto;
import food.delivery.dto.response.GetResponse;
import food.delivery.dto.response.ValidatorDto;
import food.delivery.dto.template.ImageDto;
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
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${main.domain}")
    private String domain;


    @Override
    public ResponseEntity<?> add(ProductDto productDto) {
        try {
            Optional<Category> optional = categoryRepository.findById(productDto.getCategoryDto().getId());
            if (optional.isEmpty()) {
                return ResponseEntity.internalServerError().body("Category Id " + NOT_FOUND);
            }

            if (productDto.getActive() == null) productDto.setActive(false);
            if (productDto.getDiscount() == null) productDto.setDiscount(0.0);
            if (productDto.getPrice() == null) productDto.setPrice(0.0);

            Product product = ProductMapper.toEntity(productDto);
            productRepository.save(product);

            return ResponseEntity.ok().body(product);
        }catch (DataAccessException | PersistenceException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> addProduct(
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
                return ResponseEntity.internalServerError().body(errors);
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

            return ResponseEntity.ok().body(product);
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> allProducts(Integer limit, Integer offset) {
        try {
            List<ProductDto> productDtoList = productRepository.findAll()
                    .stream().map(ProductMapper::toDto).toList();

            List<ProductDto> result = new ArrayList<>();

            GetResponse response = new GetResponse();
            response.setCount(0);
            response.setPrevious(domain + "/product/all/?limit="
                    +limit+"&offset=0");
            response.setData(result);

            if (productDtoList.size() <= offset) {
                return ResponseEntity.ok().body(response);
            }

            for (int i = offset; i < offset+limit; i++) {
                result.add(productDtoList.get(i));
                if (productDtoList.size()-1 == i) break;
            }

            response.setCount(result.size());
            response.setData(result);
            response.setNext(productDtoList.size() >= offset+limit?domain + "/product/all/?" +
                    "limit="+limit+"&offset="+(offset+limit):null);
            response.setPrevious(domain + "/product/all/?" +
                    "limit="+limit+"&offset="+(Math.max(offset-limit, 0)));

            return ResponseEntity.ok().body(response);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> allByActive(Boolean active, Integer limit, Integer offset) {
        try {
            List<ProductDto> productDtoList = productRepository.findAllByActive(active)
                    .stream().map(ProductMapper::toDto).toList();

            List<ProductDto> result = new ArrayList<>();

            GetResponse response = new GetResponse();
            response.setCount(0);
            response.setPrevious(domain + "/product/all-active/" +
                    "?active="+active+"&limit="+limit+"&offset=0");
            response.setData(result);

            if (productDtoList.size() <= offset) {
                return ResponseEntity.ok().body(response);
            }

            for (int i = offset; i < offset+limit; i++) {
                result.add(productDtoList.get(i));
                if (productDtoList.size()-1 == i) break;
            }

            response.setCount(result.size());
            response.setData(result);
            response.setNext(productDtoList.size() >= offset+limit?domain + "/product/all-active/" +
                    "?active="+active+"&limit="+limit+"&offset="+(offset+limit):null);
            response.setPrevious(domain + "/product/all-active/" +
                    "?active="+active+"&limit="+limit+"&offset="+(Math.max(offset-limit, 0)));

            return ResponseEntity.ok().body(response);
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> getById(Integer id) {
        try {
            Optional<Product> optional = productRepository.findById(id);
            if (optional.isEmpty()) {
                return ResponseEntity.internalServerError().body(NOT_FOUND);
            }
            ProductDto productDto = ProductMapper.toDto(optional.get());
            return ResponseEntity.ok().body(productDto);
        }catch (DataAccessException | IllegalArgumentException e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> weekBestProducts() {
        // TODO

        return null;
    }


    @Override
    public ResponseEntity<?> getAllExtraProduct(Integer limit, Integer offset) {
        List<ProductDto> products = productRepository.findAllByExtra(true)
                .stream().map(ProductMapper::toDto).toList();
        List<ProductDto> result = new ArrayList<>();

        GetResponse response = new GetResponse();
        response.setCount(0);
        response.setPrevious(domain + "/product/get-all-extra/" +
                "?limit="+limit+"&offset=0");
        response.setData(result);

        if (products.size() <= offset) {
            return ResponseEntity.ok().body(response);
        }

        for (int i = offset; i < offset+limit; i++) {
            result.add(products.get(i));
            if (products.size()-1 == i) break;
        }

        response.setCount(result.size());
        response.setData(result);
        response.setNext(products.size() >= offset+limit?domain + "/product/get-all-extra/" +
                "?limit="+limit+"&offset="+(offset+limit):null);
        response.setPrevious(domain + "/product/get-all-extra/" +
                "?limit="+limit+"&offset="+(Math.max(offset-limit, 0)));

        return ResponseEntity.ok().body(response);
    }


    @Override
    public ResponseEntity<?> getAllExtraProductById(Integer id, Integer limit, Integer offset) {
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

        List<ProductDto> result = new ArrayList<>();

        GetResponse response = new GetResponse();
        response.setCount(0);
        response.setPrevious(domain + "/product/get-all-extra-by-id/" +
                "?id="+id+"&limit="+limit+"&offset=0");
        response.setData(result);

        if (products.size() <= offset) {
            return ResponseEntity.ok().body(response);
        }

        for (int i = offset; i < offset+limit; i++) {
            result.add(products.get(i));
            if (products.size()-1 == i) break;
        }

        response.setCount(result.size());
        response.setData(result);
        response.setNext(products.size() >= offset+limit?domain + "/product/get-all-extra-by-id/" +
                "?id="+id+"limit="+limit+"&offset="+(offset+limit):null);
        response.setPrevious(domain + "/product/get-all-extra-by-id/" +
                "?id="+id+"limit="+limit+"&offset="+(Math.max(offset-limit, 0)));

        return ResponseEntity.ok().body(response);
    }


    @Override
    public ResponseEntity<?> getAllBonusProductById(Integer id, Integer limit, Integer offset) {
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

        List<ProductDto> result = new ArrayList<>();

        GetResponse response = new GetResponse();
        response.setCount(0);
        response.setPrevious(domain + "/product/get-all-bonus-by-id/" +
                "?id="+id+"&limit="+limit+"&offset=0");
        response.setData(result);

        if (products.size() <= offset) {
            return ResponseEntity.ok().body(response);
        }

        for (int i = offset; i < offset+limit; i++) {
            result.add(products.get(i));
            if (products.size()-1 == i) break;
        }

        response.setCount(result.size());
        response.setData(result);
        response.setNext(products.size() >= offset+limit?domain + "/product/get-all-bonus-by-id/" +
                "?id="+id+"limit="+limit+"&offset="+(offset+limit):null);
        response.setPrevious(domain + "/product/get-all-bonus-by-id/" +
                "?id="+id+"limit="+limit+"&offset="+(Math.max(offset-limit, 0)));

        return ResponseEntity.ok().body(response);
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
    public ResponseEntity<?> deleteById(Integer id) {
        try {
            productRepository.deleteById(id);

            return ResponseEntity.ok().body("Deleted!");
        }catch (DataAccessException | IllegalArgumentException e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateProduct(ProductDto productDto) {
        try {
            if (productDto.getId() == null) {
                return ResponseEntity.ok().body("Id " + EMPTY_FIELD);
            }
            Optional<Product> optional = productRepository.findById(productDto.getId());
            if (optional.isEmpty()){
                return ResponseEntity.internalServerError().body(AppMessages.NOT_FOUND);
            }
            Optional<Category> optional1 = categoryRepository.findById(productDto.getCategoryDto().getId());
            if (optional1.isEmpty()) {
                return ResponseEntity.internalServerError().body("Category Id " + NOT_FOUND);
            }

            Product product1 = optional.get();
            List<ValidatorDto> errors = validatorService.validateProduct(productDto);
            if (!errors.isEmpty()){
                return ResponseEntity.internalServerError().body(VALIDATOR_MESSAGE);
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
            return ResponseEntity.ok().body(productDto);
        }catch (NullPointerException | IllegalArgumentException | DataAccessException | NoSuchElementException e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> setIsActive(Integer id, Boolean active) {
        try {
            Optional<Product> optional = productRepository.findById(id);
            if (optional.isEmpty()) {
                return ResponseEntity.internalServerError().body(NOT_FOUND);
            }
            Product product = optional.get();
            product.setActive(active);
            productRepository.save(product);

            return ResponseEntity.ok().body(product);
        }catch (DataAccessException | NoSuchElementException e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> setDiscount(Integer id, Double discount) {
        try {
            Optional<Product> optional = productRepository.findById(id);
            if (optional.isEmpty()) {
                return ResponseEntity.internalServerError().body(NOT_FOUND);
            }

            Product product = optional.get();
            product.setDiscount(discount);
            productRepository.save(product);

            return ResponseEntity.ok().
                    body(product.getName() + " ga " + discount + " foizli chegirma o'rnatildi");
        }catch (DataAccessException | NoSuchElementException e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
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
