package br.com.upper.product.api.modules.supplier.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.com.upper.product.api.config.SuccessResponse;
import br.com.upper.product.api.config.ValidationException;
import br.com.upper.product.api.modules.product.service.ProductService;
import br.com.upper.product.api.modules.supplier.dto.SupplierRequest;
import br.com.upper.product.api.modules.supplier.dto.SupplierResponse;
import br.com.upper.product.api.modules.supplier.model.Supplier;
import br.com.upper.product.api.modules.supplier.repository.SupplierRepository;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductService productService;

    public SupplierResponse findByIdResponse(Integer id) {
        return SupplierResponse.of(findById(id));
    }

    public List<SupplierResponse> findAll() {
        return supplierRepository.findAll().stream().map(SupplierResponse::of).collect(Collectors.toList());
    }

    public List<SupplierResponse> findByName(String name) {
        if (ObjectUtils.isEmpty(name)) {
            throw new ValidationException("The supplier name was not informed.");
        }
        return supplierRepository.findByNameContainingIgnoreCase(name).stream().map(SupplierResponse::of).collect(Collectors.toList());
    }

    public Supplier findById(Integer id) {
        return supplierRepository.findById(id).orElseThrow(() -> new ValidationException("There's no supplier for the given ID."));
    }

    public SupplierResponse save(SupplierRequest request) {
        ValidadeSupplierNameInformed(request);
        var supplier = supplierRepository.save(Supplier.of(request));
        return SupplierResponse.of(supplier);
    }

    public SupplierResponse update(SupplierRequest request, Integer id) {
        ValidadeSupplierNameInformed(request);
        ValidateInformedId(id);
        var supplier = Supplier.of(request);
        supplier.setId(id);
        supplierRepository.save(supplier);
        return SupplierResponse.of(supplier);
    }

    private void ValidadeSupplierNameInformed(SupplierRequest request) {
        if (ObjectUtils.isEmpty(request.getName())) {
            throw new ValidationException("The supplier name was not informed.");
        }
    }
    public SuccessResponse delete(Integer id) {
        ValidateInformedId(id);
        if (productService.existsBySupplierId(id)) {
            throw new ValidationException("You cannot delete this supplier because it's already defined by a product");
        }
        supplierRepository.deleteById(id);
        return SuccessResponse.create("The supplier was deleted.");
    }

    private void ValidateInformedId(Integer id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new ValidationException("The id name was not informed.");
        }
    }

}
