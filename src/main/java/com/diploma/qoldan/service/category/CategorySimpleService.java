package com.diploma.qoldan.service.category;

import com.diploma.qoldan.exception.category.CategoryNotFoundException;
import com.diploma.qoldan.model.category.Category;
import com.diploma.qoldan.repository.category.CategoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategorySimpleService {

    private final CategoryRepo repo;

    public Category findCategoryByTitle(String title) throws CategoryNotFoundException {
        Category category = repo.findByTitle(title);
        if (category == null)
            throw new CategoryNotFoundException("");
        return category;
    }

}
