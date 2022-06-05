package br.com.upper.product.api.modules.category.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.com.upper.product.api.config.ValidationException;
import br.com.upper.product.api.modules.category.dto.CategoryRequest;
import br.com.upper.product.api.modules.category.dto.CategoryResponse;
import br.com.upper.product.api.modules.category.model.Category;
import br.com.upper.product.api.modules.category.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponse findByIdResponse(Integer id) {
        return new CategoryResponse().of(findById(id));
    }
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream().map(CategoryResponse::of).collect(Collectors.toList());
    }
    public List<CategoryResponse> findByDescription(String description) {
        if (ObjectUtils.isEmpty(description)) {
            throw new ValidationException("The category description must be informed");
        }
        return categoryRepository.findByDescriptionContainingIgnoreCase(description).stream().map(CategoryResponse::of).collect(Collectors.toList());
    }

    public Category findById(Integer id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new ValidationException("The category id was not informed");
        }
        return categoryRepository.findById(id).orElseThrow(() -> new ValidationException("There's no category for the given ID."));
    }

    public CategoryResponse save(CategoryRequest request) {
        validateCategoryNameInformed(request);
        var category = categoryRepository.save(Category.of(request));
        return CategoryResponse.of(category);
    }

    private void validateCategoryNameInformed(CategoryRequest request) {
        if (ObjectUtils.isEmpty(request.getDescription())) {
            throw new ValidationException("The category description was not informed.");
        }
    }

}
