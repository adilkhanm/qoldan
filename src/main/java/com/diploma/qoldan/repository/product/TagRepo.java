package com.diploma.qoldan.repository.product;

import com.diploma.qoldan.model.product.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepo extends JpaRepository<Tag, Integer> {
    Tag findByTitle(String title);
    Tag findById(Long id);
}
