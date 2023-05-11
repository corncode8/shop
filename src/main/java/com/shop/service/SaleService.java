package com.shop.service;

import com.shop.model.*;
import com.shop.repository.SaleItemRepository;
import com.shop.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaleService {

    @Autowired
    UserPageService userPageService;
    @Autowired
    SaleRepository saleRepository;
    @Autowired
    SaleItemRepository saleItemRepository;

    /** 회원가입 하면 판매자 당 판매내역 하나 생성  */
    public void createSale (User user){

        Sale sale = Sale.createSale(user);

        saleRepository.save(sale);
    }

    /** id에 해당하는 판매아이템 찾기 */
    public List<SaleItem> findSellerSaleItems (int sellerId) {

        return saleItemRepository.findSaleItemsBySellerId(sellerId);
    }

    // 판매자 id에 해당하는 Sale 찾기
    public Sale findSaleById (int sellerId) { return saleRepository.findBySellerId(sellerId); }


    /** 판매내역에 저장 (장바구니 전체 주문) */
    @Transactional
    public SaleItem addSale (int itemId, int sellerId, CartItem cartItem) {

        User seller = userPageService.findUser(sellerId);
        Sale sale = saleRepository.findBySellerId(sellerId);
        sale.setTotalCount(sale.getTotalCount()+cartItem.getCount());
        saleRepository.save(sale);
        SaleItem saleItem = SaleItem.createSaleItem(itemId, sale, seller, cartItem);
        saleItemRepository.save(saleItem);

        return saleItem;
    }

    /** 판매내역에 저장 (상품 개별 주문) */
    @Transactional
    public SaleItem addSale (int sellerId, Product item, int count) {

        User seller = userPageService.findUser(sellerId);
        Sale sale = saleRepository.findBySellerId(sellerId);
        sale.setTotalCount(sale.getTotalCount()+count);
        saleRepository.save(sale);
        SaleItem saleItem = SaleItem.createSaleItem(item.getId(), sale, seller, item, count);
        saleItemRepository.save(saleItem);

        return saleItem;
    }

}
