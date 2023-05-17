package com.diploma.qoldan.service.product;

import com.diploma.qoldan.enums.ProductStatusEnum;
import com.diploma.qoldan.exception.product.ProductNotFoundException;
import com.diploma.qoldan.model.product.Product;
import com.diploma.qoldan.repository.product.ProductRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void bookProduct(Product product) {
        product.setStatus(ProductStatusEnum.BOOKED.toString());
        repo.save(product);
    }

    @Transactional
    public void soldProduct(Product product) {
        product.setStatus(ProductStatusEnum.SOLD.toString());
        repo.save(product);
    }

    @Transactional
    public void unbookProduct(Product product) {
        product.setStatus(ProductStatusEnum.ACTIVE.toString());
        repo.save(product);
    }
}
