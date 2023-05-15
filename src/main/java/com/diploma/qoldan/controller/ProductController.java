package com.diploma.qoldan.controller;

import com.diploma.qoldan.dto.PostRequestResponse;
import com.diploma.qoldan.dto.ProductDto;
import com.diploma.qoldan.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5001/")
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<?> getProducts() {
        List<ProductDto> productDtoList = service.getProducts();
        return ResponseEntity.ok(productDtoList);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<?> getProductsByUser(@PathVariable("email") String userEmail) {
        List<ProductDto> productDtoList = service.getProductsByUser(userEmail);
        return ResponseEntity.ok(productDtoList);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable("id") Long categoryId) {
        List<ProductDto> productDtoList = service.getProductsByCategory(categoryId);
        return ResponseEntity.ok(productDtoList);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto) {
        Long id = service.createProduct(productDto);
        return ResponseEntity.ok(new PostRequestResponse(id, "Product was successfully created"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editProduct(@PathVariable("id") Long productId, @RequestBody ProductDto productDto) {
        productDto.setId(productId);
        service.editProduct(productDto);
        return ResponseEntity.ok("Product was successfully updated");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long productId) {
        service.deleteProductById(productId);
        return ResponseEntity.ok("Product was successfully deleted");
    }

}
