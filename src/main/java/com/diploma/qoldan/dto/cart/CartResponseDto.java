package com.diploma.qoldan.dto.cart;

import com.diploma.qoldan.dto.product.ProductShortResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {
    private Long id;
    private Integer total;
    private Integer totalProducts;
    private List<ProductShortResponseDto> products;
}
