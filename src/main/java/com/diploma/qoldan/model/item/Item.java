package com.diploma.qoldan.model.item;

import com.diploma.qoldan.model.category.Category;
import com.diploma.qoldan.model.image.Image;
import com.diploma.qoldan.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String title;

    private String summary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "main_image_id", referencedColumnName = "id")
    private Image mainImage;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "item")
    private List<ItemImage> itemImageList;

}
