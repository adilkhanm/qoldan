package com.diploma.qoldan.repository;

import com.diploma.qoldan.model.Category;
import com.diploma.qoldan.model.Product;
import com.diploma.qoldan.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findAllByUser(User user);
    List<Product> findAllByCategory(Category category);
    Product findById(Long id);

    long deleteById(Long id);
}
