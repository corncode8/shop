package com.shop.repository;

import com.shop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findOrdersByUserId(int id);
}