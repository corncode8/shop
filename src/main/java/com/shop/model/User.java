package com.shop.model;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.shop.feedback.Feedback;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

// ORM - Object Relation Mapping


@Builder
@Data
@Entity
@NoArgsConstructor  // 파라미터가 없는 기본 생성자를 만들어줌
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만들어줌
public class User {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    // 판매자가 가지고 있는 상품들
    @OneToMany(mappedBy = "seller")
    private List<Product> products = new ArrayList<>();


    // 구매자의 장바구니
    @OneToOne(mappedBy = "user")
    private Cart cart;

    // 구매자의 주문
    @OneToMany(mappedBy = "user")
    private List<Order> userOrder = new ArrayList<>();

    // 구매자의 주문상품들
    @OneToMany(mappedBy = "user")
    private List<OrderItem> userOrderItem = new ArrayList<>();

    // 판매자의 판매상품들
    @OneToMany(mappedBy = "seller")
    private List<SaleItem> sellerSaleItem = new ArrayList<>();

    // 판매자의 판매
    @OneToMany(mappedBy = "seller")
    private List<Sale> sellerSale;

    // 구매자의 피드백
    @OneToMany(mappedBy = "user")
    private List<Feedback> user_feedbacks;

    private int coin; // 구매자 - 충전한 돈 / 판매자 - 수익
    @Column(unique = true) // 닉네임 중복 안됨
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String role; //ROLE_USER, ROLE_ADMIN
    // OAuth를 위해 구성한 추가 필드 2개
    private String provider;    // google or naver
    private String providerId;  // sub=118145074675864377136
    @CreationTimestamp
    private Timestamp createDate;
    @UpdateTimestamp
    private Timestamp updateDate;

    @Builder
    public User(String username, String password, String email, String role, String provider, String providerId, Timestamp createDate) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }


}