package com.shop.repository;

import com.shop.model.Cart;
import com.shop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;


public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    public CartItem findByCartIdAndItemId(int cartId, int itemId);
    public CartItem findCartItemById(int id);
    public List<CartItem> findCartItemByItemId(int id);

}