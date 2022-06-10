package br.com.upper.product.api.modules.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import br.com.upper.product.api.config.SuccessResponse;
import br.com.upper.product.api.config.ValidationException;
import br.com.upper.product.api.modules.category.service.CategoryService;
import br.com.upper.product.api.modules.product.dto.ProductQuantityDto;
import br.com.upper.product.api.modules.product.dto.ProductRequest;
import br.com.upper.product.api.modules.product.dto.ProductResponse;
import br.com.upper.product.api.modules.product.dto.ProductSalesResponse;
import br.com.upper.product.api.modules.product.dto.ProductStockDto;
import br.com.upper.product.api.modules.product.model.Product;
import br.com.upper.product.api.modules.product.repository.ProductRepository;
import br.com.upper.product.api.modules.sales.client.SalesClient;
import br.com.upper.product.api.modules.sales.dto.SalesConfirmationDTO;
import br.com.upper.product.api.modules.sales.dto.SalesProductResponse;
import br.com.upper.product.api.modules.sales.enums.SalesStatus;
import br.com.upper.product.api.modules.sales.rabbitmq.SalesConfirmationSender;
import br.com.upper.product.api.modules.supplier.service.SupplierService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {

    private static final Integer ZERO = 0;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SalesConfirmationSender salesConfirmationSender;
    @Autowired
    private SalesClient salesClient;

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

    /**
     * Atualiza stock e notifica rabbit dessa alteração
     * @param product
     */
    public void updateProductStock(ProductStockDto product) {
        try{
            validateStockUpdateData(product);
            updateStock(product);
        }catch (Exception ex) {
            log.error("Error while trying to update stock for message with error: {}", ex.getMessage(), ex);
            salesConfirmationSender.sendSalesConfirmationMessage(new SalesConfirmationDTO(product.getSalesId(), SalesStatus.REJECTED));
        }
    }
    @Transactional
    void updateStock(ProductStockDto product) {
        var productsForUpdate = new ArrayList<Product>();
        product.getProducts().forEach(salesProduct -> {
            var existingProduct = findById(salesProduct.getProductId());
            validateQuantityInStock(salesProduct, existingProduct);
            existingProduct.updateStock(salesProduct.getQuantity());
            productsForUpdate.add(existingProduct);
        });
        if (!ObjectUtils.isEmpty(productsForUpdate)) {
            productRepository.saveAll(productsForUpdate);
            var approvedMessage = new SalesConfirmationDTO(product.getSalesId(), SalesStatus.APPROVED);
            salesConfirmationSender.sendSalesConfirmationMessage(approvedMessage);
        }
    }

    @Transactional // TODO: Ver a possibilidade de ser assim mesmo
    void validateStockUpdateData(ProductStockDto product) {
        if (ObjectUtils.isEmpty(product) || ObjectUtils.isEmpty(product.getSalesId())) {
            throw new ValidationException("The product data and sales ID must be informed.");
        }
        if (ObjectUtils.isEmpty(product.getProducts())) {
            throw new ValidationException("The sales products must be informed.");
        }
        product.getProducts().forEach(salesProduct -> {
            if (ObjectUtils.isEmpty(salesProduct.getQuantity()) || ObjectUtils.isEmpty(salesProduct.getProductId())) {
                throw new ValidationException("The productId and quantity must be informed.");
            }
        });
    }

    private void validateQuantityInStock(ProductQuantityDto salesProduct, Product existingProduct) {
        if (salesProduct.getQuantity() > existingProduct.getQuantityAvailable()) {
            throw new ValidationException(String.format("The product %s is out of stock.", existingProduct.getId()));
        }
    }

    public ProductSalesResponse findProductSales(Integer id) {
        var product = findById(id);
        try {
            var sales = salesClient
                    .findSalesByProductId(product.getId())
                    .orElseThrow(() -> new ValidationException("The sales was not found by this product"));
            return ProductSalesResponse.of(product, sales.getSales());
        } catch(Exception ex) {
            throw new ValidationException("There was an error trying to get the product's sales.");
        }

    }
}
