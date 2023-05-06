package com.shop.model;

import com.shop.handler.OutOfStockException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="seller_id")
    private User seller_user; // 판매자 아이디

    private int price;
    private String name;
    private String content;

    private int count;      // 조회수
    private int like_cnt;   // 찜하기 횟수
    private int stock;      // 재고 수량
    private boolean isSoldout;

    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems = new ArrayList<>();

    private String imgName; // 이미지 파일명

    private String imgPath; // 이미지 조회 경로

    public void removeStock(int stockNumber) {
        int restStock = this.stock - stockNumber;
        if (restStock < 0 ) {
            throw new OutOfStockException("상품의 재고가 부족합니다.");
        }
        this.stock = restStock;
    }
}
