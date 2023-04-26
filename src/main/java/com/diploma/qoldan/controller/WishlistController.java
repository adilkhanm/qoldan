package com.diploma.qoldan.controller;

import com.diploma.qoldan.dto.ProductDto;
import com.diploma.qoldan.model.Product;
import com.diploma.qoldan.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService service;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getUserWishlist(Authentication auth) {
        List<ProductDto> productDtoList = service.getUserWishlist(auth.getName());
        return ResponseEntity.ok(productDtoList);
    }

    @PostMapping
    public ResponseEntity<String> addToWishlist(
            @RequestParam(value = "productId", required = true) Long productId,
            Authentication auth) {
        try {
            service.addProductToWishlist(auth.getName(), productId);
            return ResponseEntity.ok("Product was added to user's wishlist");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is some error while adding item to the wishlist\n" + e.getMessage());
        }

    }

    @DeleteMapping
    public ResponseEntity<String> deleteFromWishlist(
            @RequestParam(value = "productId", required = true) Long productId,
            Authentication auth) {
        service.deleteProductFromWishlist(auth.getName(), productId);
        return ResponseEntity.ok("Product was deleted from user's wishlist");
    }
}
