package com.diploma.qoldan.repository.cart;

import com.diploma.qoldan.model.cart.Cart;
import com.diploma.qoldan.model.cart.CartProduct;
import com.diploma.qoldan.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartProductRepo extends JpaRepository<CartProduct, Integer> {
    CartProduct findByCartAndProduct(Cart cart, Product product);
    List<CartProduct> findByProduct(Product product);

    void deleteAllByCart(Cart cart);
}
