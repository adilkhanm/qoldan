package com.diploma.qoldan.controller.product;

import com.diploma.qoldan.dto.product.ProductRequestDto;
import com.diploma.qoldan.dto.product.ProductResponseDto;
import com.diploma.qoldan.dto.product.ProductShortResponseDto;
import com.diploma.qoldan.exception.category.CategoryNotFoundException;
import com.diploma.qoldan.exception.product.ProductAccessDeniedException;
import com.diploma.qoldan.exception.product.ProductIsNotActiveException;
import com.diploma.qoldan.exception.product.ProductNotFoundException;
import com.diploma.qoldan.exception.product.ProductTypeNotFoundException;
import com.diploma.qoldan.service.image.ImageService;
import com.diploma.qoldan.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;
    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<List<ProductShortResponseDto>> getProducts(
                                        @RequestParam(value = "username", required = false) String ownerUsername,
                                        @RequestParam(value = "type", required = false) String type,
                                        @RequestParam(value = "category", required = false) String category,
                                        @RequestParam(value = "priceLow", required = false) Integer priceLow,
                                        @RequestParam(value = "priceHigh", required = false) Integer priceHigh,
                                        @RequestParam(value = "limit", required = false) Integer limit,
                                        @RequestParam(value = "offset", required = false) Integer offset,
                                        Authentication auth) {
        String username = auth != null ? auth.getName() : null;
        List<ProductShortResponseDto> productResponseDtoList = service.getProducts(username, ownerUsername, type, category, priceLow, priceHigh, limit, offset);
        return ResponseEntity.ok(productResponseDtoList);
    }

    @GetMapping("/pages")
    public ResponseEntity<Integer> getProductPages(
            @RequestParam(value = "perPage") Integer perPage,
            @RequestParam(value = "username", required = false) String ownerUsername,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "priceLow", required = false) Integer priceLow,
            @RequestParam(value = "priceHigh", required = false) Integer priceHigh
    ) {
        Integer pages = service.getProductPages(perPage, ownerUsername, type, category, priceLow, priceHigh);
        return ResponseEntity.ok(pages);
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
                                              @RequestParam(value = "image") MultipartFile image,
                                              Authentication auth)
            throws ProductTypeNotFoundException, CategoryNotFoundException, UsernameNotFoundException, IOException {
        Long imageId = imageService.save(image);
        Long id = service.createProduct(productRequestDto, imageId, auth.getName());
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
