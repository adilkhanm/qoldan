package com.diploma.qoldan.controller.category;

import com.diploma.qoldan.dto.category.CategoryDto;
import com.diploma.qoldan.exception.category.CategoryExistsException;
import com.diploma.qoldan.exception.category.CategoryNotFoundException;
import com.diploma.qoldan.service.category.CategoryService;
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
    public ResponseEntity<?> getCategories() {
        List<CategoryDto> categoryDtoList = service.getCategories();
        return ResponseEntity.ok(categoryDtoList);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto) throws CategoryExistsException {
        Long id = service.createCategory(categoryDto);
        return ResponseEntity.ok(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable("id") Long categoryId, @RequestBody CategoryDto categoryDto)
            throws CategoryNotFoundException {
        categoryDto.setId(categoryId);
        service.updateCategory(categoryDto);
        return ResponseEntity.ok("Category was successfully updated");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId)
            throws CategoryNotFoundException {
        service.deleteCategoryById(categoryId);
        return ResponseEntity.ok("Category was successfully deleted");
    }

}
