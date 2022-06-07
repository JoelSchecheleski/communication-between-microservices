package br.com.upper.product.api.modules.category.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.com.upper.product.api.config.SuccessResponse;
import br.com.upper.product.api.config.ValidationException;
import br.com.upper.product.api.modules.category.dto.CategoryRequest;
import br.com.upper.product.api.modules.category.dto.CategoryResponse;
import br.com.upper.product.api.modules.category.model.Category;
import br.com.upper.product.api.modules.category.repository.CategoryRepository;
import br.com.upper.product.api.modules.product.service.ProductService;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

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
        ValidateInformedId(id);
        return categoryRepository.findById(id).orElseThrow(() -> new ValidationException("There's no category for the given ID."));
    }

    public CategoryResponse save(CategoryRequest request) {
        validateCategoryNameInformed(request);
        var category = categoryRepository.save(Category.of(request));
        return CategoryResponse.of(category);
    }

    public CategoryResponse update(CategoryRequest request, Integer id) {
        validateCategoryNameInformed(request);
        ValidateInformedId(id);
        var category = Category.of(request);
        category.setId(id);
        categoryRepository.save(category);
        return CategoryResponse.of(category);
    }

    private void validateCategoryNameInformed(CategoryRequest request) {
        if (ObjectUtils.isEmpty(request.getDescription())) {
            throw new ValidationException("The category description was not informed.");
        }
    }

    public SuccessResponse delete(Integer id) {
        ValidateInformedId(id);
        if (productService.existsByCategoryId(id)) {
            throw new ValidationException("You cannot delete this category because it's already defined by a product");
        }
        categoryRepository.deleteById(id);
        return SuccessResponse.create("The category was deleted.");
    }

    private void ValidateInformedId(Integer id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new ValidationException("The id name was not informed.");
        }
    }

}
