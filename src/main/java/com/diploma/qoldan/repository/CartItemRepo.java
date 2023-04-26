package com.diploma.qoldan.repository;

import com.diploma.qoldan.model.CartItem;
import com.diploma.qoldan.model.Product;
import com.diploma.qoldan.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Integer> {
    List<CartItem> findAllByUser(User user);
    CartItem findByUserAndProduct(User user, Product product);

    @Query("select sum(quantity) from CartItem where Product = ?1")
    Optional<Integer> sumQuantityByProduct(Product product);

    long deleteByUserAndProduct(User user, Product product);

    @Modifying
    @Query("delete from CartItem c where c.bookingEnds < ?1")
    void deleteAllExpired(Timestamp timestamp);
}
