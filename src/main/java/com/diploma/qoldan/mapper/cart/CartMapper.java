package com.diploma.qoldan.mapper.cart;

import com.diploma.qoldan.dto.cart.CartResponseDto;
import com.diploma.qoldan.dto.product.ProductShortResponseDto;
import com.diploma.qoldan.mapper.product.ProductMapper;
import com.diploma.qoldan.model.cart.Cart;
import com.diploma.qoldan.model.cart.CartProduct;
import com.diploma.qoldan.model.product.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartMapper {

    public CartResponseDto mapCartAndProductsToResponse(Cart cart, List<ProductShortResponseDto> productDtoList) {
        return CartResponseDto.builder()
                .id(cart.getId())
                .total(cart.getTotal())
                .totalProducts(cart.getTotalProducts())
                .products(productDtoList)
                .build();
    }
}
