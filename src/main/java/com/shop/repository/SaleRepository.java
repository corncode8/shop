package com.shop.repository;

import com.shop.model.Sale;
import com.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Integer> {

    public Sale findById(int id);
    public Sale findBySellerId(int id);
}
