package com.shop28.repository;

import com.shop28.entity.Color;
import com.shop28.entity.ProductDetail;
import com.shop28.entity.Size;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    List<ProductDetail> findByProductId(Integer productId, Pageable pageable);

    Boolean existsByProductIdAndColorAndSize(Integer productId, Color color, Size size);
}
