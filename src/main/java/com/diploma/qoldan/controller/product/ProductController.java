package com.diploma.qoldan.controller.product;

import com.diploma.qoldan.dto.product.ProductRequestDto;
import com.diploma.qoldan.dto.product.ProductResponseDto;
import com.diploma.qoldan.dto.product.ProductShortResponseDto;
import com.diploma.qoldan.exception.UsernameExistsException;
import com.diploma.qoldan.exception.category.CategoryNotFoundException;
import com.diploma.qoldan.exception.product.ProductAccessDeniedException;
import com.diploma.qoldan.exception.product.ProductIsNotActiveException;
import com.diploma.qoldan.exception.product.ProductNotFoundException;
import com.diploma.qoldan.exception.product.ProductTypeNotFoundException;
import com.diploma.qoldan.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5001/")
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<?> getProducts(@RequestParam(value = "username", required = false) String username,
                                         @RequestParam(value = "type", required = false) String type,
                                         @RequestParam(value = "category", required = false) String category,
                                         @RequestParam(value = "price_low", required = false) Integer price_low,
                                         @RequestParam(value = "price_high", required = false) Integer price_high,
                                         @RequestParam(value = "limit", required = false) Integer limit,
                                         @RequestParam(value = "offset", required = false) Integer offset) {
        List<ProductShortResponseDto> productResponseDtoList = service.getProducts(username, type, category, price_low, price_high, limit, offset);
        return ResponseEntity.ok(productResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Long productId) throws ProductNotFoundException {
        ProductResponseDto productResponseDto = service.getProductById(productId);
        return ResponseEntity.ok(productResponseDto);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDto productRequestDto,
                                           Authentication auth)
            throws ProductTypeNotFoundException, CategoryNotFoundException, UsernameExistsException {
        Long id = service.createProduct(productRequestDto, auth.getName());
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long productId,
                                           @RequestBody ProductRequestDto productRequestDto,
                                           Authentication auth)
            throws ProductNotFoundException, ProductIsNotActiveException, ProductTypeNotFoundException, CategoryNotFoundException, ProductAccessDeniedException {
        productRequestDto.setId(productId);
        service.updateProduct(productRequestDto, auth.getName(), auth.getAuthorities());
        return ResponseEntity.ok("Product was successfully updated");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long productId)
            throws ProductNotFoundException, ProductIsNotActiveException {
        service.deleteProductById(productId);
        return ResponseEntity.ok("Product was successfully deleted");
    }

}
