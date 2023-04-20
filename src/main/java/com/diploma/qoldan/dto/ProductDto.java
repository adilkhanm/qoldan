package com.diploma.qoldan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String title;
    private String desc;
    private Integer price;
    private Integer quantity;
    private String imageUrl;

    private String ownerEmail;
    private String ownerName;

    private Long categoryId;
    private String categoryTitle;

}
