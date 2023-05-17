package com.diploma.qoldan.mapper.product;

import com.diploma.qoldan.dto.product.ProductResponseDto;
import com.diploma.qoldan.dto.product.ProductShortResponseDto;
import com.diploma.qoldan.model.item.Item;
import com.diploma.qoldan.model.product.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public ProductShortResponseDto mapProductToShortResponse(Product product,
                                                             Boolean inWishlist,
                                                             Boolean inCart) {
        return ProductShortResponseDto.builder()
                .id(product.getId())
                .title(product.getItem().getTitle())
                .price(product.getPrice())
                .img(product.getItem().getMainImage().getUrl())
                .date(product.getDatePosted())
                .inWishlist(inWishlist)
                .inCart(inCart)
                .build();
    }

    public ProductResponseDto mapProductToResponse(Product product,
                                                   Item item,
                                                   List<String> tags,
                                                   List<String> images,
                                                   Boolean inWishlist,
                                                   Boolean inCart) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .title(item.getTitle())
                .summary(item.getSummary())
                .img(item.getMainImage().getUrl())
                .category(item.getCategory().getTitle())
                .username(item.getUser().getEmail())
                .status(product.getStatus())
                .price(product.getPrice())
                .date(product.getDatePosted())
                .type(product.getProductType().getTitle())
                .tags(tags)
                .images(images)
                .inWishlist(inWishlist)
                .inCart(inCart)
                .build();
    }

    public ProductShortResponseDto mapProductAndOrderToShortResponse(Product product, Boolean buyConfirmed, Boolean sellConfirmed) {
        return ProductShortResponseDto.builder()
                .id(product.getId())
                .title(product.getItem().getTitle())
                .price(product.getPrice())
                .img(product.getItem().getMainImage().getUrl())
                .date(product.getDatePosted())
                .inWishlist(false)
                .inCart(false)
                .buyConfirmed(buyConfirmed)
                .sellConfirmed(sellConfirmed)
                .build();
    }

}
