package com.diploma.qoldan.model.product;

import com.diploma.qoldan.model.cart.CartProduct;
import com.diploma.qoldan.model.item.Item;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(1)
    private Integer price;

    private Date datePosted;

    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductType productType;

    @OneToMany(mappedBy = "product")
    private List<ProductTag> productTagList;

    @OneToMany(mappedBy = "product")
    private List<CartProduct> cartProductList;
}
