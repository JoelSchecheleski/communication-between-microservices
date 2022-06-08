package br.com.upper.product.api.modules.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductQuantityDto {

    private Integer productId;
    private Integer quantity;

}
