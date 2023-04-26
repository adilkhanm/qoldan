package com.diploma.qoldan.repository;


import com.diploma.qoldan.model.Product;
import com.diploma.qoldan.model.User;
import com.diploma.qoldan.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepo extends JpaRepository<Wishlist, Integer> {
    List<Wishlist> findAllByUser(User user);
    Wishlist findByUserAndProduct(User user, Product product);
}
