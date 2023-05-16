package com.diploma.qoldan.repository.category;

import com.diploma.qoldan.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
    Category findById(Long id);
    Category findByTitle(String title);
}
