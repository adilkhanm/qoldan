package com.diploma.qoldan.repository.item;

import com.diploma.qoldan.model.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepo extends JpaRepository<Item, Integer> {

}
