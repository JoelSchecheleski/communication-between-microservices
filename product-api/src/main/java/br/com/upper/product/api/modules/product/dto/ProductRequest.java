package br.com.upper.product.api.modules.product.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductRequest {

    private String name;
    @JsonProperty("quantity_available")
    private Integer quantityAvailable;

    @JsonProperty("supplier_id")
    private Integer supplierId;

    @JsonProperty("category_id")
    private Integer categoryId;
}
