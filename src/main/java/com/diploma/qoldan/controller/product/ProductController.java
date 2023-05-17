package com.diploma.qoldan.controller.product;

import com.diploma.qoldan.dto.product.ProductRequestDto;
import com.diploma.qoldan.dto.product.ProductResponseDto;
import com.diploma.qoldan.dto.product.ProductShortResponseDto;
import com.diploma.qoldan.exception.category.CategoryNotFoundException;
import com.diploma.qoldan.exception.product.ProductAccessDeniedException;
import com.diploma.qoldan.exception.product.ProductIsNotActiveException;
import com.diploma.qoldan.exception.product.ProductNotFoundException;
import com.diploma.qoldan.exception.product.ProductTypeNotFoundException;
import com.diploma.qoldan.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5001/")
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductShortResponseDto>> getProducts(
                                        @RequestParam(value = "username", required = false) String ownerUsername,
                                        @RequestParam(value = "type", required = false) String type,
                                        @RequestParam(value = "category", required = false) String category,
                                        @RequestParam(value = "price_low", required = false) Integer price_low,
                                        @RequestParam(value = "price_high", required = false) Integer price_high,
                                        @RequestParam(value = "limit", required = false) Integer limit,
                                        @RequestParam(value = "offset", required = false) Integer offset,
                                        Authentication auth) {
        String username = auth != null ? auth.getName() : null;
        List<ProductShortResponseDto> productResponseDtoList = service.getProducts(username, ownerUsername, type, category, price_low, price_high, limit, offset);
        return ResponseEntity.ok(productResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable("id") Long productId,
                                                         Authentication auth)
            throws ProductNotFoundException {
        String username = auth != null ? auth.getName() : null;
        ProductResponseDto productResponseDto = service.getProductById(username, productId);
        return ResponseEntity.ok(productResponseDto);
    }

    @GetMapping("/my")
    public ResponseEntity<List<ProductShortResponseDto>> getUsersProducts(@RequestParam(value = "limit", required = false) Integer limit,
                                                                     @RequestParam(value = "offset", required = false) Integer offset,
                                                                     Authentication auth) {
        List<ProductShortResponseDto> responseDtoList = service.getUsersProducts(auth.getName(), limit, offset);
        return ResponseEntity.ok(responseDtoList);
    }

    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody ProductRequestDto productRequestDto,
                                              Authentication auth)
            throws ProductTypeNotFoundException, CategoryNotFoundException, UsernameNotFoundException {
        Long id = service.createProduct(productRequestDto, auth.getName());
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable("id") Long productId,
                                                @RequestBody ProductRequestDto productRequestDto,
                                           Authentication auth)
            throws ProductNotFoundException, ProductIsNotActiveException, ProductTypeNotFoundException, CategoryNotFoundException, ProductAccessDeniedException {
        productRequestDto.setId(productId);
        service.updateProduct(productRequestDto, auth.getName(), auth.getAuthorities());
        return ResponseEntity.ok("Product was successfully updated");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId)
            throws ProductNotFoundException, ProductIsNotActiveException {
        service.deleteProductById(productId);
        return ResponseEntity.ok("Product was successfully deleted");
    }

}
