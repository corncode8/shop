package com.shop.repository;

import com.shop.model.Cart;
import com.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    public Product findById(int id);
    public Product findItemById(int id);
}
