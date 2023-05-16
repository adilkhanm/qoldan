package com.diploma.qoldan.service.category;

import com.diploma.qoldan.dto.category.CategoryDto;
import com.diploma.qoldan.exception.category.CategoryExistsException;
import com.diploma.qoldan.exception.category.CategoryNotFoundException;
import com.diploma.qoldan.mapper.category.CategoryMapper;
import com.diploma.qoldan.model.category.Category;
import com.diploma.qoldan.repository.category.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Long createCategory(CategoryDto categoryDto) throws CategoryExistsException {
        Category category = repo.findByTitle(categoryDto.getTitle());
        if (category != null) {
            throw new CategoryExistsException("");
        }

        category = mapper.mapDtoToCategory(categoryDto);
        repo.save(category);
        return category.getId();
    }

    @Transactional
    public void updateCategory(CategoryDto categoryDto) throws CategoryNotFoundException {
        Category category = repo.findById(categoryDto.getId());
        if (category == null) {
            throw new CategoryNotFoundException("");
        }

        category = mapper.mapDtoToCategory(categoryDto);
        repo.save(category);
    }

    @Transactional
    public void deleteCategoryById(Long categoryId) throws CategoryNotFoundException {
        Category category = repo.findById(categoryId);
        if (category == null) {
            throw new CategoryNotFoundException("");
        }

        repo.delete(category);
    }
}
