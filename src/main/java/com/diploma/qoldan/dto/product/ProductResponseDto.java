package com.diploma.qoldan.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    private Long id;

    private String title;
    private String summary;
    private Long imageId;

    private String category;
    private String username;

    private String status;
    private Integer price;
    private Date date;

    private String type;
    private List<String> tags;
    private List<Long> images;

    private Boolean inWishlist;
    private Boolean inCart;
    private String orderStatus;
}
