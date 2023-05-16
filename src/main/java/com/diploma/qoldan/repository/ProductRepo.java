package com.diploma.qoldan.repository;

import com.diploma.qoldan.model.category.Category;
import com.diploma.qoldan.model.product.Product;
import com.diploma.qoldan.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    Product findById(Long id);
    List<Product> findAllByStatus(String status);
}
