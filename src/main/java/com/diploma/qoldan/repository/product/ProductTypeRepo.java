package com.diploma.qoldan.repository.product;

import com.diploma.qoldan.model.product.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepo extends JpaRepository<ProductType, Integer> {
    ProductType findByTitle(String title);
    ProductType findById(Long id);
}
