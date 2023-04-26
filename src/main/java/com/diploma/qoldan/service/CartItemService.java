package com.diploma.qoldan.service;

import com.diploma.qoldan.dto.ProductDto;
import com.diploma.qoldan.mapper.ProductMapper;
import com.diploma.qoldan.model.CartItem;
import com.diploma.qoldan.model.Product;
import com.diploma.qoldan.model.User;
import com.diploma.qoldan.repository.CartItemRepo;
import com.diploma.qoldan.repository.ProductRepo;
import com.diploma.qoldan.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepo repo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    private final ProductMapper productMapper;

    @Transactional
    public List<ProductDto> getUsersCartProducts(String username) {
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        repo.deleteAllExpired(new Timestamp(System.currentTimeMillis()));
        List<CartItem> cartItems = repo.findAllByUser(user);
        List<Product> productList = cartItems.stream().map(CartItem::getProduct).toList();
        return productList
                .stream()
                .map(productMapper::mapProductToDto)
                .toList();
    }

    @Transactional
    public void bookProduct(String username, Long productId, Integer quantity) throws Exception {
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Product product = productRepo.findById(productId);
        repo.deleteAllExpired(new Timestamp(System.currentTimeMillis()));
        Integer leftQuantity = product.getQuantity() - repo.sumQuantityByProduct(product).orElse(0);

        CartItem cartItem = repo.findByUserAndProduct(user, product);
        if (cartItem != null)
            leftQuantity += cartItem.getQuantity();
        else
            cartItem = CartItem.builder()
                    .product(product)
                    .user(user)
                    .build();
        if (quantity > leftQuantity)
            throw new Exception("There is no such amount of product left!");

        cartItem.setQuantity(quantity);
        cartItem.setBookedTime(new Timestamp(System.currentTimeMillis()));
        cartItem.setBookingEnds(new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)));
        repo.save(cartItem);
    }

    @Transactional
    public void unbookProduct(String username, Long productId) {
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Product product = productRepo.findById(productId);
        repo.deleteAllExpired(new Timestamp(System.currentTimeMillis()));
        CartItem cartItem = repo.findByUserAndProduct(user, product);
        repo.delete(cartItem);
    }

}
