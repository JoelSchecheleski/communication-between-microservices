package br.com.upper.product.api.modules.product.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockDto {

    private String salesId;
    private List<ProductQuantityDto> products;

}
