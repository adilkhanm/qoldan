package com.diploma.qoldan.service;

import com.diploma.qoldan.mapper.ProductMapper;
import com.diploma.qoldan.model.cart.CartProduct;
import com.diploma.qoldan.model.product.Product;
import com.diploma.qoldan.model.user.User;
import com.diploma.qoldan.repository.CartProductRepo;
import com.diploma.qoldan.repository.ProductRepo;
import com.diploma.qoldan.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CartProductService {

    private final CartProductRepo repo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    private final ProductMapper productMapper;

//    public List<ProductDto> getUsersCartProducts(String username) {
//        User user = userRepo.findByEmail(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("");
//        }
//        repo.deleteAllExpired(new Timestamp(System.currentTimeMillis()));
//        List<CartProduct> cartItems = repo.findAllByUser(user);
//        List<Product> productList = cartItems.stream().map(CartProduct::getProduct).toList();
//        return productList
//                .stream()
//                .map(productMapper::mapProductToDto)
//                .toList();
//    }

//    @Transactional
//    public void bookProduct(String username, Long productId) throws ProductNotAvailableException {
//        User user = userRepo.findByEmail(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("");
//        }
//        Product product = productRepo.findById(productId);
//        repo.deleteAllExpired(new Timestamp(System.currentTimeMillis()));
//
//        CartProduct cartItem = repo.findByProduct(product);
//        if (cartItem != null) {
//            throw new ProductNotAvailableException("");
//        } else {
//            cartItem = CartProduct.builder()
//                    .product(product)
//                    .user(user)
//                    .build();
//        }
//
//        cartItem.setBookStart(new Timestamp(System.currentTimeMillis()));
//        cartItem.setBookEnd(new Timestamp(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30)));
//        repo.save(cartItem);
//    }

//    @Transactional
//    public void unbookProduct(String username, Long productId) {
//        User user = userRepo.findByEmail(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("");
//        }
//        Product product = productRepo.findById(productId);
//        repo.deleteAllExpired(new Timestamp(System.currentTimeMillis()));
//        CartProduct cartItem = repo.findByUserAndProduct(user, product);
//        repo.delete(cartItem);
//    }

}
