package br.com.upper.product.api.modules.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.com.upper.product.api.config.SuccessResponse;
import br.com.upper.product.api.config.ValidationException;
import br.com.upper.product.api.modules.category.service.CategoryService;
import br.com.upper.product.api.modules.product.dto.ProductRequest;
import br.com.upper.product.api.modules.product.dto.ProductResponse;
import br.com.upper.product.api.modules.product.dto.ProductStockDto;
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

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream().map(ProductResponse::of).collect(Collectors.toList());
    }

    public ProductResponse findByIdResponse(Integer id) {
        return ProductResponse.of(findById(id));
    }
    public List<ProductResponse> findByName(String name) {
       return productRepository.findByNameContainingIgnoreCase(name).stream().map(ProductResponse::of).collect(Collectors.toList());
    }

    public List<ProductResponse> findBySupplierId(Integer supplierId) {
        if(ObjectUtils.isEmpty(supplierId)) {
            throw  new ValidationException("The product's supplier was not informed");
        }
        return productRepository.findBySupplierId(supplierId).stream().map(ProductResponse::of).collect(Collectors.toList());
    }

    public List<ProductResponse> findByCategoryId(Integer categoryId) {
        if(ObjectUtils.isEmpty(categoryId)) {
            throw  new ValidationException("The product's category was not informed");
        }
        return productRepository.findByCategoryId(categoryId).stream().map(ProductResponse::of).collect(Collectors.toList());
    }

    public Product findById(Integer id) {
        if(ObjectUtils.isEmpty(id)) {
            throw  new ValidationException("The product's id was not informed");
        }
        return productRepository.findById(id).orElseThrow(() -> new ValidationException("There's no product for the given ID."));
    }

    public ProductResponse save(ProductRequest request){
        validateDataInformed(request);
        validateCategoryAndSupplierId(request);
        var category = categoryService.findById(request.getCategoryId());
        var supplier = supplierService.findById(request.getSupplierId());
        var product = productRepository.save(Product.of(request, supplier, category));

        return ProductResponse.of(product);
    }

    public ProductResponse update(ProductRequest request, Integer id){
        ValidateInformedId(id);
        validateDataInformed(request);
        validateCategoryAndSupplierId(request);

        var category = categoryService.findById(request.getCategoryId());
        var supplier = supplierService.findById(request.getSupplierId());

        var product = Product.of(request, supplier, category);
        product.setId(id);
        productRepository.save(product);

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
    public Boolean existsByCategoryId(Integer id) {
        if(ObjectUtils.isEmpty(id)) {
            throw  new ValidationException("The category id was not informed");
        }
        return productRepository.existsByCategoryId(id);
    }

    public Boolean existsBySupplierId(Integer id) {
        if(ObjectUtils.isEmpty(id)) {
            throw  new ValidationException("The supplier id was not informed");
        }
        return productRepository.existsByCategoryId(id);
    }

    public SuccessResponse delete(Integer id) {
        ValidateInformedId(id);
        productRepository.deleteById(id);
        return SuccessResponse.create("The product was deleted.");
    }

    private void ValidateInformedId(Integer id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new ValidationException("The id was not informed.");
        }
    }

    public void updateProductStock(ProductStockDto product) {

    }
}
