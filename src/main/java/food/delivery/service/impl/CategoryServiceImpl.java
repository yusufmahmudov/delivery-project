package food.delivery.service.impl;

import food.delivery.dto.CategoryDto;
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
import org.springframework.dao.DataAccessException;
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

    @Override
    public ResponseDto<String> addCategory(CategoryDto categoryDto) {
        try {
            if (categoryDto.getId() != null) {
                return ResponseDto.<String>builder()
                        .success(false)
                        .message("id bo'sh bo'lishi kerak " + AppMessages.VALIDATOR_MESSAGE)
                        .code(AppCode.VALIDATOR_ERROR)
                        .build();
            }

            Category category = CategoryMapper.toEntityWithoutProduct(categoryDto);
            categoryRepository.save(category);

            return ResponseDto.<String>builder()
                    .code(AppCode.OK)
                    .message(AppMessages.SAVED)
                    .success(true)
                    .build();
        }catch (DataAccessException | PersistenceException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseDto.<String>builder()
                    .code(AppCode.ERROR)
                    .message(e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<List<CategoryDto>> allCategory() {
        try {
            List<CategoryDto> categoryDtos = categoryRepository.findAll()
                    .stream().map(CategoryMapper::toDtoWithoutProduct).toList();

            return ResponseDto.<List<CategoryDto>>builder()
                    .data(categoryDtos)
                    .code(AppCode.OK)
                    .message(categoryDtos.isEmpty() ? AppMessages.NOT_FOUND : AppMessages.OK)
                    .success(true)
                    .build();
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseDto.<List<CategoryDto>>builder()
                    .code(AppCode.ERROR)
                    .message(e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<List<CategoryDto>> allCategoryWithProducts() {
        try {
            List<CategoryDto> categoryDtos = categoryRepository.findAll()
                    .stream().map(CategoryMapper::toDto).toList();

            return ResponseDto.<List<CategoryDto>>builder()
                    .data(categoryDtos)
                    .message(categoryDtos.isEmpty() ? AppMessages.NOT_FOUND : AppMessages.OK)
                    .code(AppCode.OK)
                    .success(true)
                    .build();
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseDto.<List<CategoryDto>>builder()
                    .message(e.getMessage())
                    .code(AppCode.ERROR)
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<List<CategoryDto>> getAllCategoriesWithActiveProducts(Boolean active) {
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

            return ResponseDto.<List<CategoryDto>>builder()
                    .data(categoryDtos)
                    .message(categoryDtos.isEmpty() ? AppMessages.NOT_FOUND : AppMessages.OK)
                    .code(AppCode.OK)
                    .success(true)
                    .build();
        }catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseDto.<List<CategoryDto>>builder()
                    .message(e.getMessage())
                    .code(AppCode.ERROR)
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<CategoryDto> getById(Integer id) {
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

            return ResponseDto.<CategoryDto>builder()
                    .data(categoryDto)
                    .success(true)
                    .message(AppMessages.OK)
                    .code(AppCode.OK)
                    .build();
        }catch (DataAccessException | IllegalArgumentException | NoSuchElementException e) {
            return ResponseDto.<CategoryDto>builder()
                    .message(e.getMessage())
                    .code(AppCode.ERROR)
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<CategoryDto> getByIdWithProducts(Integer id) {
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

            return ResponseDto.<CategoryDto>builder()
                    .data(categoryDto)
                    .success(true)
                    .message(AppMessages.OK)
                    .code(AppCode.OK)
                    .build();
        }catch (DataAccessException | IllegalArgumentException | NoSuchElementException e) {
            return ResponseDto.<CategoryDto>builder()
                    .message(e.getMessage())
                    .code(AppCode.ERROR)
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<String> deleteById(Integer id) {
        try {
            categoryRepository.deleteById(id);

            return ResponseDto.<String>builder()
                    .success(true)
                    .message(AppMessages.OK)
                    .code(AppCode.OK)
                    .build();
        }catch (DataAccessException | IllegalArgumentException e) {
            log.error(e.getMessage());
            return ResponseDto.<String>builder()
                    .message(e.getMessage())
                    .code(AppCode.ERROR)
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<CategoryDto> updateCategory(CategoryDto categoryDto) {
        try {
            if (categoryDto.getId() == null) {
                return ResponseDto.<CategoryDto>builder()
                        .message("ID " + AppMessages.EMPTY_FIELD)
                        .code(AppCode.NOT_FOUND)
                        .success(false)
                        .build();
            }

            Category category = CategoryMapper.toEntityWithoutProduct(categoryDto);
            categoryRepository.save(category);

            return ResponseDto.<CategoryDto>builder()
                    .message(AppMessages.OK)
                    .success(true)
                    .code(AppCode.OK)
                    .data(categoryDto)
                    .build();
        }catch (DataAccessException | HibernateException e) {
            log.error(e.getMessage());
            return ResponseDto.<CategoryDto>builder()
                    .message(e.getMessage())
                    .code(AppCode.ERROR)
                    .success(false)
                    .build();
        }
    }



}
