package com.diploma.qoldan.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductShortResponseDto {
    private Long id;
    private String title;
    private Integer price;
    private String img;
    private Date date;
    private Boolean inWishlist;
    private Boolean inCart;
}
