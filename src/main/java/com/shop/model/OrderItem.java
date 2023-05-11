package com.shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user; // 구매자

    private int productId; // 주문 상품 번호
    private String itemName; // 주문 상품 이름
    private int productPrice; // 주문 상품 가격
    private int productCount; // 주문 상품 수량
    private int productTotalPrice; // 가격*수량

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="saleItem_id")
    private SaleItem saleItem; // 주문상품에 매핑되는 판매상품

    private int isCancel; // 주문 취소 여부 (0:주문완료 / 1:주문취소)

    // 장바구니 전체 주문
    public static OrderItem createOrderItem(int productId, User user, CartItem cartItem, SaleItem saleItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(productId);
        orderItem.setUser(user);
        orderItem.setItemName(cartItem.getProduct().getName());
        orderItem.setProductPrice(cartItem.getProduct().getPrice());
        orderItem.setProductCount(cartItem.getCount());
        orderItem.setProductTotalPrice(cartItem.getProduct().getPrice()*cartItem.getCount());
        orderItem.setSaleItem(saleItem);
        return orderItem;
    }

    // 상품 개별 주문
    public static OrderItem createOrderItem(int productId, User user, Product item, int count, Order order, SaleItem saleItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(productId);
        orderItem.setUser(user);
        orderItem.setOrder(order);
        orderItem.setItemName(item.getName());
        orderItem.setProductPrice(item.getPrice());
        orderItem.setProductCount(count);
        orderItem.setProductTotalPrice(item.getPrice()*count);
        orderItem.setSaleItem(saleItem);
        return orderItem;
    }


}
