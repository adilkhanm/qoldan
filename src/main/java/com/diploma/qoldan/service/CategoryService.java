package com.diploma.qoldan.service;

import com.diploma.qoldan.dto.CategoryDto;
import com.diploma.qoldan.mapper.CategoryMapper;
import com.diploma.qoldan.model.Category;
import com.diploma.qoldan.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepo repo;

    private final CategoryMapper mapper;


    public List<CategoryDto> getCategories() {
        List<Category> categories = repo.findAll();
        return categories
                .stream()
                .map(mapper::mapCategoryToDto)
                .toList();
    }

    public void createCategory(CategoryDto categoryDto) {
        Category category = mapper.mapDtoToCategory(categoryDto);
        repo.save(category);
    }

    public void editCategory(CategoryDto categoryDto) {
        Category category = mapper.mapDtoToCategory(categoryDto);
        repo.save(category);
    }

    public void deleteCategoryById(Long categoryId) {
        repo.deleteById(categoryId);
    }
}
