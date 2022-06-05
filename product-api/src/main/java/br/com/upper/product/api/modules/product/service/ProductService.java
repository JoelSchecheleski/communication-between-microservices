package br.com.upper.product.api.modules.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.com.upper.product.api.config.ValidationException;
import br.com.upper.product.api.modules.category.service.CategoryService;
import br.com.upper.product.api.modules.product.dto.ProductRequest;
import br.com.upper.product.api.modules.product.dto.ProductResponse;
import br.com.upper.product.api.modules.product.model.Product;
import br.com.upper.product.api.modules.product.repository.ProductRepository;
import br.com.upper.product.api.modules.supplier.service.SupplierService;

@Service
public class ProductService {

    private static final Integer ZERO = 0;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private CategoryService categoryService;

    public ProductResponse save(ProductRequest request){
        validateDataInformed(request);
        validateCategoryAndSupplierId(request);
        var category = categoryService.findById(request.getCategoryId());
        var supplier = supplierService.findById(request.getSupplierId());
        var product = productRepository.save(Product.of(request, supplier, category));

        return ProductResponse.of(product);
    }

    private void validateDataInformed(ProductRequest request) {
        if(ObjectUtils.isEmpty(request.getName())) {
            throw  new ValidationException("The product's name was not informed");
        }
        if(ObjectUtils.isEmpty(request.getQuantityAvailable())) {
            throw  new ValidationException("The product's quantity was not informed");
        }
        if(request.getQuantityAvailable() <= ZERO) {
            throw  new ValidationException("The quantity of products informed is not valid");
        }
    }

    private void validateCategoryAndSupplierId(ProductRequest request) {
        if(ObjectUtils.isEmpty(request.getCategoryId())) {
            throw  new ValidationException("The category ID was not informed");
        }
        if(ObjectUtils.isEmpty(request.getSupplierId())) {
            throw  new ValidationException("The supplier ID was not informed");
        }
    }
}
