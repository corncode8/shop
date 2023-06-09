package com.shop.repository;

import com.shop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    public Cart findById(int id);
    public Cart findByUserId(int id);
    public Cart findCartByUserId(int id);
}