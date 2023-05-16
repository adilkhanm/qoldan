package com.diploma.qoldan.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    private Long id;

    private String title;
    private String summary;
    private String img;
    private String category;
    private List<String> images;

    private Integer price;
    private String type;
    private List<String> tags;
}
