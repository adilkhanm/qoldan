package com.diploma.qoldan.controller;

import com.diploma.qoldan.dto.CategoryDto;
import com.diploma.qoldan.dto.ProductDto;
import com.diploma.qoldan.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5001/")
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<CategoryDto> categoryDtoList = service.getCategories();
        return ResponseEntity.ok(categoryDtoList);
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody CategoryDto categoryDto) {
        service.createCategory(categoryDto);
        return ResponseEntity.ok("Category was successfully created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editCategory(@PathVariable("id") Long categoryId, @RequestBody CategoryDto categoryDto) {
        categoryDto.setId(categoryId);
        service.editCategory(categoryDto);
        return ResponseEntity.ok("Category was successfully updated");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId) {
        service.deleteCategoryById(categoryId);
        return ResponseEntity.ok("Category was successfully deleted");
    }

}
