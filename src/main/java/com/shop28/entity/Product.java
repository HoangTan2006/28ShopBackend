package com.shop28.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends AbstractEntity<Integer> {
    @Column(name = "name")
    private String name;

    @JoinColumn(name = "category_id")
    @ManyToOne
    private Category category;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "star")
    private Float star;

    @Column(name = "price")
    private Integer price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductDetail> productDetails = new HashSet<>();
}
