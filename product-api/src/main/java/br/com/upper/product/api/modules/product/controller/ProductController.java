package br.com.upper.product.api.modules.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.upper.product.api.config.SuccessResponse;
import br.com.upper.product.api.config.ValidationException;
import br.com.upper.product.api.modules.product.dto.ProductCheckStockRequest;
import br.com.upper.product.api.modules.product.dto.ProductRequest;
import br.com.upper.product.api.modules.product.dto.ProductResponse;
import br.com.upper.product.api.modules.product.dto.ProductSalesResponse;
import br.com.upper.product.api.modules.product.dto.ProductStockDto;
import br.com.upper.product.api.modules.product.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @GetMapping("{id}")
    public ProductResponse findByIdResponse(@PathVariable Integer id) {
        return productService.findByIdResponse(id);
    }

    @GetMapping("name/{name}")
    public List<ProductResponse> findByName(@PathVariable String name) {
        return productService.findByName(name);
    }

    @GetMapping("supplier/{supplierId}")
    public List<ProductResponse> findBySupplierId(@PathVariable Integer supplierId) {
        return productService.findBySupplierId(supplierId);
    }

    @GetMapping("category/{categoryId}")
    public List<ProductResponse> findByCategoryId(@PathVariable Integer categoryId) {
        return productService.findByCategoryId(categoryId);
    }

    @PostMapping
    public ProductResponse save(@RequestBody ProductRequest request) {
        return productService.save(request);
    }

    @DeleteMapping("{id}")
    public SuccessResponse delete(@PathVariable Integer id) {
        return productService.delete(id);
    }

    @PutMapping("{id}")
    public ProductResponse update(@RequestBody ProductRequest request,
                                   @PathVariable Integer id) {
        return productService.update(request, id);
    }

    @PostMapping("check-stock")
    public SuccessResponse checkProductsStock(@RequestBody ProductCheckStockRequest request) {
        return productService.checkProductsStock(request);
    }

    @GetMapping("{id}/sales")
    public ProductSalesResponse findProductSales(@PathVariable Integer id) {
        try {
            return productService.findProductSales(id);
        } catch(Exception ex) {
            throw new ValidationException(String.format("Can't find products sale by id: {}", ex));
        }
    }

}
