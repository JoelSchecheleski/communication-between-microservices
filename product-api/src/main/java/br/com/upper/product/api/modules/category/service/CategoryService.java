package br.com.upper.product.api.modules.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.com.upper.product.api.config.ValidationException;
import br.com.upper.product.api.modules.category.dto.CategoryRequest;
import br.com.upper.product.api.modules.category.dto.CategoryResponse;
import br.com.upper.product.api.modules.category.repository.CategoryRepository;
import br.com.upper.product.api.modules.category.model.Category;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

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
