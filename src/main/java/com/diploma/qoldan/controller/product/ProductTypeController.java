package com.diploma.qoldan.controller.product;

import com.diploma.qoldan.dto.product.ProductTypeDto;
import com.diploma.qoldan.exception.product.ProductTypeExistsException;
import com.diploma.qoldan.exception.product.ProductTypeNotFoundException;
import com.diploma.qoldan.service.product.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-type")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:5001/")
public class ProductTypeController {

    private final ProductTypeService service;

    @GetMapping
    public ResponseEntity<List<ProductTypeDto>> getProductTypes() {
        List<ProductTypeDto> productTypeDtoList = service.getProductTypes();
        return ResponseEntity.ok(productTypeDtoList);
    }

    @PostMapping
    public ResponseEntity<Long> createProductType(
            @RequestBody ProductTypeDto productTypeDto)
            throws ProductTypeExistsException {
        Long id = service.createProductType(productTypeDto);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProductType(
            @PathVariable("id") Long productTypeId,
            @RequestBody ProductTypeDto productTypeDto)
            throws ProductTypeNotFoundException {
        productTypeDto.setId(productTypeId);
        service.updateProductType(productTypeDto);
        return ResponseEntity.ok("Product type was successfully updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductType(@PathVariable("id") Long productTypeId)
            throws ProductTypeNotFoundException {
        service.deleteProductTypeById(productTypeId);
        return ResponseEntity.ok("Product type was successfully deleted");
    }

}
