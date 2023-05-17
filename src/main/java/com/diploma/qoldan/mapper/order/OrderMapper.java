package com.diploma.qoldan.mapper.order;

import com.diploma.qoldan.dto.order.OrderResponseDto;
import com.diploma.qoldan.dto.product.ProductShortResponseDto;
import com.diploma.qoldan.model.order.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderResponseDto mapOrderToResponse(Order order, List<ProductShortResponseDto> productDtos) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .timestamp(order.getPurchasedTime())
                .total(order.getTotal())
                .totalProducts(order.getTotalProducts())
                .status(order.getStatus())
                .address(order.getAddress().toString())
                .products(productDtos)
                .build();
    }
}
