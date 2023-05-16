package com.diploma.qoldan.controller.wishlist;

import com.diploma.qoldan.dto.product.ProductShortResponseDto;
import com.diploma.qoldan.exception.product.ProductNotFoundException;
import com.diploma.qoldan.exception.wishlist.WishlistExistsException;
import com.diploma.qoldan.exception.wishlist.WishlistNotFoundException;
import com.diploma.qoldan.service.wishlist.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/my-wishlist")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5001/")
public class WishlistController {

    private final WishlistService service;

    @GetMapping
    public ResponseEntity<?> getUserWishlist(@RequestParam(value = "limit", required = false) Integer limit,
                                             @RequestParam(value = "offset", required = false) Integer offset,
                                             Authentication auth) {
        List<ProductShortResponseDto> productDtoList = service.getUsersWishlist(auth.getName(), limit, offset);
        return ResponseEntity.ok(productDtoList);
    }

    @PostMapping
    public ResponseEntity<String> addToWishlist(@RequestParam(value = "productId") Long productId,
                                                Authentication auth)
            throws WishlistExistsException, ProductNotFoundException {
        service.addProductToWishlist(auth.getName(), productId);
        return ResponseEntity.ok("Product was added to user's wishlist");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteFromWishlist(@RequestParam(value = "productId") Long productId,
                                                     Authentication auth)
            throws WishlistNotFoundException, ProductNotFoundException {
        service.deleteProductFromWishlist(auth.getName(), productId);
        return ResponseEntity.ok("Product was deleted from user's wishlist");
    }
}
