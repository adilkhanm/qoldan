package com.diploma.qoldan.repository.cart;

import com.diploma.qoldan.model.cart.Cart;
import com.diploma.qoldan.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {
    Cart findByUser(User user);
}
