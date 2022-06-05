package br.com.upper.product.api.modules.supplier.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.com.upper.product.api.config.ValidationException;
import br.com.upper.product.api.modules.supplier.dto.SupplierRequest;
import br.com.upper.product.api.modules.supplier.dto.SupplierResponse;
import br.com.upper.product.api.modules.supplier.model.Supplier;
import br.com.upper.product.api.modules.supplier.repository.SupplierRepository;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier findById(Integer id) {
        return supplierRepository.findById(id).orElseThrow(() -> new ValidationException("There's no supplier for the given ID."));
    }

    public SupplierResponse save(SupplierRequest request) {
        ValidadeSupplierNameInformed(request);
        var supplier = supplierRepository.save(Supplier.of(request));
        return SupplierResponse.of(supplier);
    }

    private void ValidadeSupplierNameInformed(SupplierRequest request) {
        if (ObjectUtils.isEmpty(request.getName())) {
            throw new ValidationException("The supplier name was not informed.");
        }
    }

}
