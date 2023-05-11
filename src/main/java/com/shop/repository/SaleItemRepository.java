package com.shop.repository;

import com.shop.model.SaleItem;
import com.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleItemRepository extends JpaRepository<SaleItem, Integer> {

    List<SaleItem> findSaleItemsBySellerId(int sellerId);
    List<SaleItem> findAll();
}
