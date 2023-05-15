package com.diploma.qoldan.controller;

import com.diploma.qoldan.dto.ProductDto;
import com.diploma.qoldan.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5001/")
public class CartController {

    private final CartItemService service;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getUsersCartProducts(Authentication auth) {
        List<ProductDto> productDtoList = service.getUsersCartProducts(auth.getName());
        return ResponseEntity.ok(productDtoList);
    }

    @PostMapping
    public ResponseEntity<String> addProductToCart(
            @RequestParam(value = "productId", required = true) Long productId,
            @RequestParam(value = "quantity", required = true) Integer quantity,
            Authentication auth) {
        try {
            service.bookProduct(auth.getName(), productId, quantity);
            return ResponseEntity.ok("The product was added to the cart");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is some error while adding product to the cart\n" + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProductFromCart(
            @RequestParam(value = "productId", required = true) Long productId,
            Authentication auth) {
        service.unbookProduct(auth.getName(), productId);
        return ResponseEntity.ok("The product was deleted from the cart");
    }

}
