package com.diploma.qoldan.repository.wishlist;


import com.diploma.qoldan.model.product.Product;
import com.diploma.qoldan.model.user.User;
import com.diploma.qoldan.model.wishlist.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepo extends JpaRepository<Wishlist, Integer> {
    List<Wishlist> findAllByUser(User user);
    Wishlist findByUserAndProduct(User user, Product product);
}
