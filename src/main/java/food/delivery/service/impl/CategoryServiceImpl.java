package food.delivery.service.impl;

import food.delivery.dto.CategoryDto;
import food.delivery.dto.response.GetResponse;
import food.delivery.dto.response.ResponseDto;
import food.delivery.helper.AppCode;
import food.delivery.helper.AppMessages;
import food.delivery.model.Category;
import food.delivery.model.Product;
import food.delivery.repository.CategoryRepository;
import food.delivery.service.CategoryService;
import food.delivery.service.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Value("${main.domain}")
    private String domain;


    @Override
    public ResponseEntity<?> addCategory(CategoryDto categoryDto) {
        try {
            if (categoryDto.getId() != null) {
                return ResponseEntity.badRequest()
                        .body("id bo'sh bo'lishi kerak " + AppMessages.VALIDATOR_MESSAGE);
            }

            Category category = CategoryMapper.toEntityWithoutProduct(categoryDto);
            categoryRepository.save(category);

            return ResponseEntity.ok().body(category);
        }catch (DataAccessException | PersistenceException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> allCategory(Integer limit, Integer offset) {
        try {
            List<CategoryDto> categoryDtos = categoryRepository.findAll()
                    .stream().map(CategoryMapper::toDtoWithoutProduct).toList();
            List<CategoryDto> result = new ArrayList<>();

            GetResponse response = new GetResponse();
            response.setCount(0);
            response.setPrevious(domain + "/category/get-page/?limit="
                    +limit+"&offset=0");
            response.setData(result);

            if (categoryDtos.size() <= offset) {
                return ResponseEntity.ok().body(response);
            }

            for (int i = offset; i < offset+limit; i++) {
                result.add(categoryDtos.get(i));
                if (categoryDtos.size()-1 == i) break;
            }

            response.setCount(result.size());
            response.setData(result);
            response.setNext(categoryDtos.size() >= offset+limit?domain + "/category/get-page/?limit="+limit
                    +"&offset="+(offset+limit):null);
            response.setPrevious(domain + "/category/get-page/?limit="+limit+"&offset=" + (Math.max(offset-limit, 0)));

            return ResponseEntity.ok().body(response);
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> allCategoryWithProducts(Integer limit, Integer offset) {
        try {
            List<CategoryDto> categoryDtos = categoryRepository.findAll()
                    .stream().map(CategoryMapper::toDto).toList();
            List<CategoryDto> result = new ArrayList<>();

            GetResponse response = new GetResponse();
            response.setCount(0);
            response.setPrevious(domain + "/category/all-category-and-product/?limit="
                    +limit+"&offset=0");
            response.setData(result);

            if (categoryDtos.size() <= offset) {
                return ResponseEntity.ok().body(response);
            }

            for (int i = offset; i < offset+limit; i++) {
                result.add(categoryDtos.get(i));
                if (categoryDtos.size()-1 == i) break;
            }

            response.setCount(result.size());
            response.setData(result);
            response.setNext(categoryDtos.size() > offset+limit?domain
                    + "/category/all-category-and-product/?limit="+limit
                    + "&offset="+(offset+limit):null);
            response.setPrevious(domain + "/category/all-category-and-product/?limit="
                    +limit+"&offset=" + (Math.max(offset-limit, 0)));

            return ResponseEntity.ok().body(response);
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getAllCategoriesWithActiveProducts(Boolean active, Integer limit, Integer offset) {
        try {
            List<Category> categories = categoryRepository.findAll();
            for (Category category : categories) {
                List<Product> activeProducts = new ArrayList<>();
                for (Product product : category.getProducts()) {
                    if (product.getActive() == active) {
                        activeProducts.add(product);
                    }
                }
                category.setProducts(activeProducts);
            }

            List<CategoryDto> categoryDtos = categories.stream().map(CategoryMapper::toDto).toList();
            List<CategoryDto> result = new ArrayList<>();

            GetResponse response = new GetResponse();
            response.setCount(0);
            response.setPrevious(domain + "/all-category-and-product-active/?active="
                                        + active +"&limit=" +limit+"&offset=0");
            response.setData(result);

            if (categoryDtos.size() <= offset) {
                return ResponseEntity.ok().body(response);
            }

            for (int i = offset; i < offset+limit; i++) {
                result.add(categoryDtos.get(i));
                if (categoryDtos.size()-1 == i) break;
            }

            response.setCount(result.size());
            response.setData(result);
            response.setNext(categoryDtos.size() >= offset+limit ?
                    domain + "/category/all-category-and-product-active/?active="
                    + active +"&limit="+limit+"&offset="+(offset+limit):null);
            response.setPrevious(domain + "/category/all-category-and-product-active/?active="
                    + active +"&limit="+limit+"&offset="+(Math.max(offset-limit, 0)));

            return ResponseEntity.ok().body(response);
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> getById(Integer id) {
        try {
            Optional<Category> optional = categoryRepository.findById(id);
            if (optional.isEmpty()) {
                ResponseDto.<CategoryDto>builder()
                        .message(AppMessages.NOT_FOUND)
                        .code(AppCode.NOT_FOUND)
                        .success(false)
                        .build();
            }
            CategoryDto categoryDto = CategoryMapper.toDtoWithoutProduct(optional.get());

            return ResponseEntity.ok().body(categoryDto);
        }catch (DataAccessException | IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> getByIdWithProducts(Integer id) {
        try {
            Optional<Category> optional = categoryRepository.findById(id);
            if (optional.isEmpty()) {
                ResponseDto.<CategoryDto>builder()
                        .message(AppMessages.NOT_FOUND)
                        .code(AppCode.NOT_FOUND)
                        .success(false)
                        .build();
            }
            CategoryDto categoryDto = CategoryMapper.toDto(optional.get());

            return ResponseEntity.ok().body(categoryDto);
        }catch (DataAccessException | IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> deleteById(Integer id) {
        try {
            categoryRepository.deleteById(id);

            return ResponseEntity.ok().body("Deleted");
        }catch (DataAccessException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> updateCategory(CategoryDto categoryDto) {
        try {
            if (categoryDto.getId() == null) {
                return ResponseEntity.badRequest().body("ID topilmadi");
            }

            Category category = CategoryMapper.toEntityWithoutProduct(categoryDto);
            categoryRepository.save(category);

            return ResponseEntity.ok().body(category);
        }catch (DataAccessException | HibernateException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }



}
