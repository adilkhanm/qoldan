package com.diploma.qoldan.controller;

import com.diploma.qoldan.dto.ProductDto;
import com.diploma.qoldan.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> productDtoList = service.getProducts();
        return ResponseEntity.ok(productDtoList);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<ProductDto>> getProductsByUser(@PathVariable("email") String userEmail) {
        List<ProductDto> productDtoList = service.getProductsByUser(userEmail);
        return ResponseEntity.ok(productDtoList);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable("id") Long categoryId) {
        List<ProductDto> productDtoList = service.getProductsByCategory(categoryId);
        return ResponseEntity.ok(productDtoList);
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductDto productDto) {
        service.createProduct(productDto);
        return ResponseEntity.ok("Product was successfully created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editProduct(@PathVariable("id") Long productId, @RequestBody ProductDto productDto) {
        productDto.setId(productId);
        service.editProduct(productDto);
        return ResponseEntity.ok("Product was successfully updated");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId) {
        service.deleteProductById(productId);
        return ResponseEntity.ok("Product was successfully deleted");
    }

}
