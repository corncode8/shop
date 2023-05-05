package com.shop.repository;

import com.shop.model.Cart;
import com.shop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    public CartItem findById(int id);
}