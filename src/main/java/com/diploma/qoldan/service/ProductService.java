package com.diploma.qoldan.service;

import com.diploma.qoldan.dto.ProductDto;
import com.diploma.qoldan.mapper.ProductMapper;
import com.diploma.qoldan.model.Category;
import com.diploma.qoldan.model.Product;
import com.diploma.qoldan.model.User;
import com.diploma.qoldan.repository.CategoryRepo;
import com.diploma.qoldan.repository.ProductRepo;
import com.diploma.qoldan.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo repo;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;

    private final ProductMapper mapper;

    public List<ProductDto> getProducts() {
        List<Product> products = repo.findAll();
        return products
                .stream()
                .map(mapper::mapProductToDto)
                .toList();
    }

    public List<ProductDto> getProductsByUser(String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Product> products = repo.findAllByUser(user);
        return products
                .stream()
                .map(mapper::mapProductToDto)
                .toList();
    }

    public List<ProductDto> getProductsByCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId);
        List<Product> products = repo.findAllByCategory(category);
        return products
                .stream()
                .map(mapper::mapProductToDto)
                .toList();
    }

    public void createProduct(ProductDto productDto) {
        Category category = categoryRepo.findById(productDto.getCategoryId());
        User user = userRepo.findByEmail(productDto.getOwnerEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Product product = mapper.mapDtoToProduct(productDto, category, user);
        repo.save(product);
    }

    public void editProduct(ProductDto productDto) {
        Category category = categoryRepo.findById(productDto.getCategoryId());
        User user = userRepo.findByEmail(productDto.getOwnerEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Product product = mapper.mapDtoToProduct(productDto, category, user);
        repo.save(product);
    }

    public void deleteProductById(Long productId) {
        repo.deleteById(productId);
    }
}
