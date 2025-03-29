package com.shop28.repository;

import com.shop28.entity.Category;
import com.shop28.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @NativeQuery("SELECT * FROM products WHERE category_id = :categoryId " +
                 "LIMIT :limit OFFSET :offset")
    List<Product> findByCategory(Integer categoryId, Integer limit, Integer offset);
}
