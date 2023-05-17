package com.diploma.qoldan.repository.order;

import com.diploma.qoldan.model.order.OrderRow;
import com.diploma.qoldan.model.product.Product;
import com.diploma.qoldan.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRowRepo extends JpaRepository<OrderRow, Integer> {
    OrderRow findByProduct(Product product);

    @Query(
            value = "select * from order_row " +
                    "where order_row.id in " +
                        "(select order_row.id from order_row " +
                        "left join product on order_row.product_id = product.id " +
                        "left join item on product.item_id = item.id " +
                        "where item.user_id = ?1)",
            nativeQuery = true
    )
    List<OrderRow> findAllByUser(Long userId);

    @Query(
            value = "select * from order_row " +
                    "where order_row.id in " +
                        "(select order_row.id from order_row " +
                        "left join product on order_row.product_id = product.id " +
                        "left join item on product.item_id = item.id " +
                        "where item.user_id = ?1 " +
                        "and order_row.sell_confirmed = ?2)",
            nativeQuery = true
    )
    List<OrderRow> findAllByUserAndStatus(Long userId, Boolean sellConfirmed);
}
