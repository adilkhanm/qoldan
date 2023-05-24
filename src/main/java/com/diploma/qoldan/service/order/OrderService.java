package com.diploma.qoldan.service.order;

import com.diploma.qoldan.dto.order.AddressDto;
import com.diploma.qoldan.dto.order.OrderResponseDto;
import com.diploma.qoldan.dto.product.ProductShortResponseDto;
import com.diploma.qoldan.enums.OrderStatusEnum;
import com.diploma.qoldan.enums.ProductStatusEnum;
import com.diploma.qoldan.exception.cart.CartIsEmptyException;
import com.diploma.qoldan.exception.cart.CartNotFoundException;
import com.diploma.qoldan.exception.order.OrderAlreadyConfirmedException;
import com.diploma.qoldan.exception.order.OrderRowNotFoundException;
import com.diploma.qoldan.exception.order.OrderStatusNotFoundException;
import com.diploma.qoldan.exception.product.ProductIsNotAvailableException;
import com.diploma.qoldan.exception.product.ProductNotFoundException;
import com.diploma.qoldan.exception.user.UserHasNoAccessException;
import com.diploma.qoldan.mapper.address.AddressMapper;
import com.diploma.qoldan.model.cart.Cart;
import com.diploma.qoldan.model.cart.CartProduct;
import com.diploma.qoldan.model.order.Order;
import com.diploma.qoldan.model.order.OrderRow;
import com.diploma.qoldan.model.product.Product;
import com.diploma.qoldan.model.user.User;
import com.diploma.qoldan.repository.order.OrderRepo;
import com.diploma.qoldan.repository.order.OrderRowRepo;
import com.diploma.qoldan.service.cart.CartSimpleService;
import com.diploma.qoldan.service.product.ProductSimpleService;
import com.diploma.qoldan.service.user.UserSimpleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepo repo;
    private final OrderRowRepo rowRepo;

    private final AddressMapper addressMapper;

    private final OrderSimpleService service;
    private final CartSimpleService cartService;
    private final UserSimpleService userService;
    private final ProductSimpleService productService;

    public List<OrderResponseDto> getPurchaseOrders(String username, String status)
            throws OrderStatusNotFoundException {
        User user = userService.findUserByUsername(username);

        List<Order> orders;
        if (status == null) {
            orders = repo.findAllByUser(user);
        } else {
            try {
                orders = repo.findAllByUserAndStatus(user, OrderStatusEnum.valueOf(status).toString());
            } catch (IllegalArgumentException e) {
                throw new OrderStatusNotFoundException("");
            }
        }

        return orders
                .stream()
                .map(service::getResponseFromOrder)
                .toList();
    }

    public List<ProductShortResponseDto> getSellProducts(String username, Boolean sellConfirmed) {
        User user = userService.findUserByUsername(username);

        List<OrderRow> orderRowList;
        if (sellConfirmed == null) {
            orderRowList = rowRepo.findAllByUser(user.getId());
        } else {
            orderRowList = rowRepo.findAllByUserAndStatus(user.getId(), sellConfirmed);
        }
        return orderRowList
                .stream()
                .map(service::getProductResponseFromOrderRow)
                .toList();
    }

    @Transactional
    public void createOrder(String username, AddressDto addressDto, String paymentId)
            throws CartNotFoundException, ProductIsNotAvailableException, CartIsEmptyException {
        User user = userService.findUserByUsername(username);

        Cart cart = cartService.findCartByUser(user);
        if (CollectionUtils.isEmpty(cart.getCartProducts()))
            throw new CartIsEmptyException("");

        Order order = Order.builder()
                .purchasedTime(new Timestamp(System.currentTimeMillis()))
                .total(cart.getTotal())
                .totalProducts(cart.getTotalProducts())
                .totalDelivery(0)
                .status(OrderStatusEnum.PENDING.toString())
                .address(addressMapper.mapDtoToAddress(addressDto))
                .paymentId(paymentId)
                .user(user)
                .build();

        List<OrderRow> orderRowList = new ArrayList<>();
        for (CartProduct cartProduct : cart.getCartProducts()) {
            Product product = cartProduct.getProduct();
            if (!product.getStatus().equals(ProductStatusEnum.BOOKED.toString()))
                throw new ProductIsNotAvailableException("Some product was not booked first!\nProduct title: " + product.getItem().getTitle());
            productService.soldProduct(product);

            OrderRow orderRow = OrderRow.builder()
                    .sellConfirmed(false)
                    .buyConfirmed(false)
                    .product(product)
                    .order(order)
                    .build();
            orderRowList.add(orderRow);
        }

        repo.save(order);
        rowRepo.saveAll(orderRowList);
        cartService.emptyCartOfUser(user);

        for (OrderRow row : orderRowList) {
            cartService.emptyCartsByProduct(row.getProduct());
        }
    }

    @Transactional
    public void confirmOrder(String username, Long orderId)
            throws OrderAlreadyConfirmedException, UserHasNoAccessException {
        Order order = repo.findById(orderId);
        if (!order.getUser().getEmail().equals(username))
            throw new UserHasNoAccessException("");
        if (order.getStatus().equals(OrderStatusEnum.CONFIRMED.toString()))
            throw new OrderAlreadyConfirmedException("");

        order.setStatus(OrderStatusEnum.CONFIRMED.toString());
        repo.save(order);
        for (OrderRow orderRow : order.getOrderRowList()) {
            orderRow.setBuyConfirmed(true);
            rowRepo.save(orderRow);
        }
    }

    @Transactional
    public void confirmOrderProduct(String username, Long productId)
            throws ProductNotFoundException, UserHasNoAccessException, OrderRowNotFoundException {
        Product product = productService.findProductById(productId);
        OrderRow orderRow = service.findRowByProduct(product);
        if (!orderRow.getOrder().getUser().getEmail().equals(username))
            throw new UserHasNoAccessException("");

        orderRow.setBuyConfirmed(true);
        rowRepo.save(orderRow);
    }

    @Transactional
    public void confirmSellProduct(String username, Long productId)
            throws ProductNotFoundException, OrderRowNotFoundException, UserHasNoAccessException {
        Product product = productService.findProductById(productId);
        OrderRow orderRow = service.findRowByProduct(product);
        if (!orderRow.getProduct().getItem().getUser().getEmail().equals(username))
            throw new UserHasNoAccessException("");

        orderRow.setSellConfirmed(true);
        rowRepo.save(orderRow);
    }
}
