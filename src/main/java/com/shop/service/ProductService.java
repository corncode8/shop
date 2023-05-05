package com.shop.service;

import com.shop.model.Cart;
import com.shop.model.CartItem;
import com.shop.model.Product;
import com.shop.repository.CartItemRepository;
import com.shop.repository.CartRepository;
import com.shop.repository.ProductRepository;
import com.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;


    // create - 상품 등록
    public void saveItem(Product item) {
        productRepository.save(item);
    }

    // read
    public Product itemView(Integer id) {   // 개별 상품 읽기
        return productRepository.findById(id).get();
    }
    public List<Product> allItemView() {    // 전체 상품 리스트 읽기
        return productRepository.findAll();
    }

    // update
    public void itemModify(Product item, int id) {
        Product update = productRepository.findById(id);
        update.setName(item.getName());
        update.setContent(item.getContent());
        update.setPrice(item.getPrice());
        update.setStock(item.getStock());
        productRepository.save(update);
    }


    // delete
    public void itemDelete(int id) {
        productRepository.deleteById(id);
    }
}
