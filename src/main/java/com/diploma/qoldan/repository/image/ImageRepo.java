package com.diploma.qoldan.repository.image;

import com.diploma.qoldan.model.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepo extends JpaRepository<Image, Integer> {
    Image findById(Long id);
}
