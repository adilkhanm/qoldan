package com.diploma.qoldan.service.order;

import com.diploma.qoldan.dto.order.OrderResponseDto;
import com.diploma.qoldan.dto.product.ProductShortResponseDto;
import com.diploma.qoldan.exception.order.OrderRowNotFoundException;
import com.diploma.qoldan.mapper.order.OrderMapper;
import com.diploma.qoldan.mapper.product.ProductMapper;
import com.diploma.qoldan.model.order.Order;
import com.diploma.qoldan.model.order.OrderRow;
import com.diploma.qoldan.model.product.Product;
import com.diploma.qoldan.repository.order.OrderRepo;
import com.diploma.qoldan.repository.order.OrderRowRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderSimpleService {

    private final OrderRepo repo;
    private final OrderRowRepo rowRepo;

    private final OrderMapper mapper;
    private final ProductMapper productMapper;

    public OrderResponseDto getResponseFromOrder(Order order) {
        List<ProductShortResponseDto> productDtoList = order.getOrderRowList()
                .stream()
                .map(this::getProductResponseFromOrderRow)
                .toList();
        return mapper.mapOrderToResponse(order, productDtoList);
    }

    public ProductShortResponseDto getProductResponseFromOrderRow(OrderRow orderRow) {
        return productMapper.mapProductAndOrderToShortResponse(
                orderRow.getProduct(),
                orderRow.getBuyConfirmed(),
                orderRow.getSellConfirmed());
    }

    public OrderRow findRowByProduct(Product product) throws OrderRowNotFoundException {
        OrderRow row = rowRepo.findByProduct(product);
        if (row == null)
            throw new OrderRowNotFoundException("No order row with product" + product.getItem().getTitle());
        return row;
    }

}
