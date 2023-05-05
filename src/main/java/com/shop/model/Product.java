package com.shop.model;

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
    private String name;
    private String content;
    private int price;
    private int count;
    private int stock;
    private boolean isSoldout;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user; // 판매자 아이디

    @OneToMany(mappedBy = "item")
    private List<CartItem> cart_items = new ArrayList<>();

    private String photo;
}
