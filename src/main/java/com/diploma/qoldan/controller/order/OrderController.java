package com.diploma.qoldan.controller.order;

import com.diploma.qoldan.dto.order.AddressDto;
import com.diploma.qoldan.dto.order.OrderResponseDto;
import com.diploma.qoldan.dto.product.ProductShortResponseDto;
import com.diploma.qoldan.exception.cart.CartIsEmptyException;
import com.diploma.qoldan.exception.cart.CartNotFoundException;
import com.diploma.qoldan.exception.order.OrderAlreadyConfirmedException;
import com.diploma.qoldan.exception.order.OrderRowNotFoundException;
import com.diploma.qoldan.exception.order.OrderStatusNotFoundException;
import com.diploma.qoldan.exception.product.ProductIsNotAvailableException;
import com.diploma.qoldan.exception.product.ProductNotFoundException;
import com.diploma.qoldan.exception.user.UserHasNoAccessException;
import com.diploma.qoldan.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/my-orders")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:5001/")
public class OrderController {

    private final OrderService service;

    @GetMapping("/purchases")
    public ResponseEntity<List<OrderResponseDto>> getPurchaseOrders(@RequestParam(name = "status", required = false) String status,
                                                                    Authentication auth)
            throws OrderStatusNotFoundException {
        List<OrderResponseDto> orderResponseDtoList = service.getPurchaseOrders(auth.getName(), status);
        return ResponseEntity.ok(orderResponseDtoList);
    }

    @GetMapping("/sells")
    public ResponseEntity<List<ProductShortResponseDto>> getSellProducts(@RequestParam(name = "sellConfirmed", required = false) Boolean sellConfirmed,
                                                                         Authentication auth) {
        List<ProductShortResponseDto> productResponseDtoList = service.getSellProducts(auth.getName(), sellConfirmed);
        return ResponseEntity.ok(productResponseDtoList);
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestParam(name = "paymentId") String paymentId,
                                              @RequestBody AddressDto addressDto,
                                              Authentication auth)
            throws CartNotFoundException, CartIsEmptyException, ProductIsNotAvailableException {
        service.createOrder(auth.getName(), addressDto, paymentId);
        return ResponseEntity.ok("Order was successfully created");
    }

    @PutMapping("confirm/{id}")
    public ResponseEntity<String> confirmOrder(@PathVariable("id") Long orderId,
                                               Authentication auth)
            throws OrderAlreadyConfirmedException, UserHasNoAccessException {
        service.confirmOrder(auth.getName(), orderId);
        return ResponseEntity.ok("Order was confirmed");
    }

    @PutMapping("/confirm/product/{id}")
    public ResponseEntity<String> confirmOrderProduct(@PathVariable("id") Long productId,
                                                      Authentication auth)
            throws UserHasNoAccessException, ProductNotFoundException, OrderRowNotFoundException {
        service.confirmOrderProduct(auth.getName(), productId);
        return ResponseEntity.ok("Order product was confirmed");
    }

    @PutMapping("/confirm/sell-product/{id}")
    public ResponseEntity<String> confirmSellProduct(@PathVariable("id") Long productId,
                                                     Authentication auth)
            throws UserHasNoAccessException, ProductNotFoundException, OrderRowNotFoundException {
        service.confirmSellProduct(auth.getName(), productId);
        return ResponseEntity.ok("Product sell was confirmed");
    }
}
