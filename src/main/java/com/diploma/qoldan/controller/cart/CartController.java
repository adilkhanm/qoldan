package com.diploma.qoldan.controller.cart;

import com.diploma.qoldan.dto.cart.CartResponseDto;
import com.diploma.qoldan.exception.cart.CartIsEmptyException;
import com.diploma.qoldan.exception.cart.CartNotFoundException;
import com.diploma.qoldan.exception.cart.CartProductExistsException;
import com.diploma.qoldan.exception.cart.CartProductNotFoundException;
import com.diploma.qoldan.exception.product.ProductIsNotAvailableException;
import com.diploma.qoldan.exception.product.ProductNotFoundException;
import com.diploma.qoldan.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/my-cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5001/")
public class CartController {

    private final CartService service;

    @GetMapping
    public ResponseEntity<CartResponseDto> getUsersCart(@RequestParam(value = "limit", required = false) Integer limit,
                                                        @RequestParam(value = "offset", required = false) Integer offset,
                                                        Authentication auth)
            throws CartNotFoundException {
        CartResponseDto cartResponseDto = service.getUsersCart(auth.getName(), limit, offset);
        return ResponseEntity.ok(cartResponseDto);
    }

    @PostMapping
    public ResponseEntity<String> addProductToCart(@RequestParam(value = "productId") Long productId,
                                                   Authentication auth)
            throws CartProductExistsException, ProductNotFoundException, CartNotFoundException, ProductIsNotAvailableException {
        service.addProductToCart(auth.getName(), productId);
        return ResponseEntity.ok("Product was added to the user's cart");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProductFromCart(@RequestParam(value = "productId") Long productId,
                                                        Authentication auth)
            throws CartProductNotFoundException, ProductNotFoundException, CartNotFoundException {
        service.deleteProductFromCart(auth.getName(), productId);
        return ResponseEntity.ok("The product was deleted from the cart");
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookProducts(Authentication auth)
            throws CartIsEmptyException, CartNotFoundException, ProductIsNotAvailableException {
        service.bookProducts(auth.getName());
        return ResponseEntity.ok("Products were successfully booked");
    }

    @PostMapping("/unbook")
    public ResponseEntity<String> unbookProducts(Authentication auth)
            throws ProductIsNotAvailableException, CartNotFoundException {
        service.unbookProducts(auth.getName());
        return ResponseEntity.ok("Products were successfully unbooked");
    }

}
