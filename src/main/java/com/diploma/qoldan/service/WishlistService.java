package com.diploma.qoldan.service;

import com.diploma.qoldan.dto.ProductDto;
import com.diploma.qoldan.mapper.ProductMapper;
import com.diploma.qoldan.model.Product;
import com.diploma.qoldan.model.User;
import com.diploma.qoldan.model.Wishlist;
import com.diploma.qoldan.repository.ProductRepo;
import com.diploma.qoldan.repository.UserRepo;
import com.diploma.qoldan.repository.WishlistRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepo repo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;

    private final ProductMapper productMapper;

    @Transactional
    public List<ProductDto> getUserWishlist(String username) {
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<Wishlist> wishlist = repo.findAllByUser(user);
        List<Product> productList = wishlist.stream().map(Wishlist::getProduct).toList();
        return productList
                .stream()
                .map(productMapper::mapProductToDto)
                .toList();
    }

    @Transactional
    public void addProductToWishlist(String username, Long productId) throws Exception {
        Product product = productRepo.findById(productId);
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Wishlist wishlist = repo.findByUserAndProduct(user, product);
        if (wishlist != null)
            throw new Exception("The product is already in wishlist");

        wishlist = new Wishlist();
        wishlist.setProduct(product);
        wishlist.setUser(user);
        repo.save(wishlist);
    }

    @Transactional
    public void deleteProductFromWishlist(String username, Long productId) {
        Product product = productRepo.findById(productId);
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Wishlist wishlist = repo.findByUserAndProduct(user, product);
        repo.delete(wishlist);
    }
}
