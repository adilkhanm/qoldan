package com.diploma.qoldan.repository;

import com.diploma.qoldan.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
    Category findById(Long id);

    long deleteById(Long id);
}
