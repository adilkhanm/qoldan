package com.diploma.qoldan.repository;

import com.diploma.qoldan.model.cart.CartProduct;
import com.diploma.qoldan.model.product.Product;
import com.diploma.qoldan.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CartProductRepo extends JpaRepository<CartProduct, Integer> {
    List<CartProduct> findAllByUser(User user);
    CartProduct findByUserAndProduct(User user, Product product);
    CartProduct findByProduct(Product product);

    @Modifying
    @Query("delete from CartProduct c where c.bookEnd < ?1")
    void deleteAllExpired(Timestamp timestamp);
}
