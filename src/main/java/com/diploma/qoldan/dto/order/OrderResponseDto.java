package com.diploma.qoldan.dto.order;

import com.diploma.qoldan.dto.product.ProductShortResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private Long id;
    private Timestamp timestamp;
    private Integer total;
    private Integer totalProducts;
    private String status;
    private String address;
    private List<ProductShortResponseDto> products;
}
