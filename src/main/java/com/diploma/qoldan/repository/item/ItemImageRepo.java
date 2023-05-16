package com.diploma.qoldan.repository.item;

import com.diploma.qoldan.model.item.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemImageRepo extends JpaRepository<ItemImage, Integer> {
}
