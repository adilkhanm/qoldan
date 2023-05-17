package com.diploma.qoldan.repository.product;

import com.diploma.qoldan.model.category.Category;
import com.diploma.qoldan.model.product.Product;
import com.diploma.qoldan.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    Product findById(Long id);
    List<Product> findAllByStatus(String status);

    @Query(
            value = "select * from product " +
                    "where product.id in " +
                        "(select product.id from product " +
                        "left join item on product.item_id = item.id " +
                        "where item.user_id = ?2 " +
                        "and product.status = ?1)",
            nativeQuery = true
    )
    List<Product> findAllByStatusAndUser(String status, Long userId);
}
