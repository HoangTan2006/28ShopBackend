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
@Table(name = "product_variants")
public class ProductVariant extends AbstractEntity<Integer> {
    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;

    @Column(name = "image_url")
    private String imageUrl;

    @JoinColumn(name = "color_id")
    @ManyToOne
    private Color color;

    @JoinColumn(name = "size_id")
    @ManyToOne
    private Size size;

    @Column(name = "price")
    private Integer price;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "sold_quantity")
    private Integer soldQuantity;

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails = new HashSet<>();
}
