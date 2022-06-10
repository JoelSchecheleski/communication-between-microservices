package br.com.upper.product.api.modules.sales.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.com.upper.product.api.modules.category.dto.CategoryResponse;
import br.com.upper.product.api.modules.product.dto.ProductSalesResponse;
import br.com.upper.product.api.modules.product.model.Product;
import br.com.upper.product.api.modules.supplier.dto.SupplierResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalesProductResponse {

    private Integer id;
    private String name;
    @JsonProperty("quantity_available")
    private Integer quantityAvailable;
    @JsonProperty("created_at")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;
    private SupplierResponse supplier;
    private CategoryResponse category;
    private List<String> sales;

    public static SalesProductResponse of(Product product, List<String> sales) {
        return SalesProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .quantityAvailable(product.getQuantityAvailable())
                .createdAt(product.getCreatedAt())
                .supplier(SupplierResponse.of(product.getSupplier()))
                .category(CategoryResponse.of(product.getCategory()))
                .sales(sales)
                .build();
    }

}
