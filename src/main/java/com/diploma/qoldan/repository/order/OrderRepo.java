package com.diploma.qoldan.repository.order;

import com.diploma.qoldan.model.order.Order;
import com.diploma.qoldan.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {

    List<Order> findAllByUser(User user);

    List<Order> findAllByUserAndStatus(User user, String status);
    Order findById(Long id);
}
