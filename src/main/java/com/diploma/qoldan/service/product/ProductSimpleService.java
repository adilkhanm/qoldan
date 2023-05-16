package com.diploma.qoldan.service.product;

import com.diploma.qoldan.exception.product.ProductNotFoundException;
import com.diploma.qoldan.model.product.Product;
import com.diploma.qoldan.repository.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductSimpleService {

    private final ProductRepo repo;

    public Product findProductById(Long id) throws ProductNotFoundException {
        Product product = repo.findById(id);
        if (product == null)
            throw new ProductNotFoundException("");
        return product;
    }

}
