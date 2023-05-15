package com.diploma.qoldan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    @NotBlank
    private String title;

    private String summary;

    @Min(1)
    private Integer price;

    @Min(1)
    private Integer quantity;

    private String imageUrl;

    private Date datePosted;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull
    private Category category;

}
